package com.gts.backcommons.jwtauth;

import com.gts.backcommons.exceptions.InactiveUserException;
import com.gts.backcommons.exceptions.GlobalExceptionHandler;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import com.gts.backcommons.ssi.dtos.RefreshTokenDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.web.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserStatusSecurityTest {
    JdbcTemplate jdbc;
    CommonUserDetailsService users;
    CustomAuthenticationProvider provider;
    JwtUtils jwt = new JwtUtils();

    @BeforeEach void setup() {
        var ds = new DriverManagerDataSource("jdbc:h2:mem:" + java.util.UUID.randomUUID() + ";DB_CLOSE_DELAY=-1", "sa", "");
        jdbc = new JdbcTemplate(ds);
        jdbc.execute("create table company(id bigint primary key, code varchar(50))");
        jdbc.execute("create table common_user(id bigint primary key, pseudo varchar(50), password varchar(255), company_id bigint, entity_status varchar(32))");
        jdbc.update("insert into company values(1, 'GTS')");
        var encoder = new BCryptPasswordEncoder(4);
        jdbc.update("insert into common_user values(1, 'alice', ?, 1, 'ACTIF')", encoder.encode("secret"));
        users = new CommonUserDetailsService(ds);
        provider = new CustomAuthenticationProvider(ds, encoder);
        SecurityContextHolder.clearContext();
    }

    @AfterEach void cleanup() { SecurityContextHolder.clearContext(); }

    private UserLoginDTO credentials(String password) {
        var dto = new UserLoginDTO(); dto.setPseudo("alice"); dto.setCompanyCode("GTS"); dto.setPassword(password); return dto;
    }

    @Test void activeUserCanLoginAndRefresh() {
        assertTrue(provider.authenticate(new UsernamePasswordAuthenticationToken("alice", credentials("secret"))).isAuthenticated());
        var dto = new RefreshTokenDTO(); dto.setRefreshToken(jwt.generateRefreshToken("alice", "GTS"));
        assertEquals(200, new AuthController(mock(AuthService.class), jwt, users).refreshToken(dto).getStatusCode().value());
    }

    @ParameterizedTest @ValueSource(strings={"ARCHIVED", "DELETED"}) @NullSource
    void inactiveUserCannotLoginRefreshOrUseExistingToken(String status) throws Exception {
        var token = jwt.generateAccessToken("alice", "GTS");
        var refresh = jwt.generateRefreshToken("alice", "GTS");
        jdbc.update("update common_user set entity_status = ?", status);
        assertFalse(users.loadUserByUsername("alice").isEnabled());
        assertThrows(InactiveUserException.class, () -> provider.authenticate(new UsernamePasswordAuthenticationToken("alice", credentials("secret"))));
        assertThrows(BadCredentialsException.class, () -> provider.authenticate(new UsernamePasswordAuthenticationToken("alice", credentials("wrong"))));
        var mvc = MockMvcBuilders.standaloneSetup(new AuthController(mock(AuthService.class), jwt, users))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        mvc.perform(post("/refresh").contentType("application/json").content("{\"refreshToken\":\"" + refresh + "\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode").value("USER_INACTIVE"));
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        var response = new MockHttpServletResponse();
        var chain = mock(jakarta.servlet.FilterChain.class);
        new JwtAuthentificationFilter(jwt, users).doFilter(request, response, chain);
        assertEquals(403, response.getStatus());
        assertTrue(response.getContentAsString().contains("USER_INACTIVE"));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verifyNoInteractions(chain);
    }

    @Test void activeTokenPassesAndReactivationRestoresAccess() throws Exception {
        var token = jwt.generateAccessToken("alice", "GTS");
        jdbc.update("update common_user set entity_status = 'ARCHIVED'");
        jdbc.update("update common_user set entity_status = 'ACTIF'");
        var request = new MockHttpServletRequest(); request.addHeader("Authorization", "Bearer " + token);
        var response = new MockHttpServletResponse(); var chain = mock(jakarta.servlet.FilterChain.class);
        new JwtAuthentificationFilter(jwt, users).doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }
}

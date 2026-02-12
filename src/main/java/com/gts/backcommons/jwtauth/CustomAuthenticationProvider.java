package com.gts.backcommons.jwtauth;

import com.gts.backcommons.ssi.constants.CommonUserConstants;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

       var userLoginDTO = (UserLoginDTO) authentication.getCredentials();
       var companyCode = userLoginDTO.getCompanyCode();
       var pseudo = userLoginDTO.getPseudo();

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);

        var query = "SELECT id, pseudo AS username, password, TRUE AS enabled " +
                "FROM common_user " +
                "WHERE lower(pseudo) = lower(?) AND company_id = (SELECT id FROM company WHERE lower(code) = lower(?))";

        var users = userDetailsManager.getJdbcTemplate().query(query, new Object[]{pseudo, companyCode}, (rs, rowNum) -> {
            String password = rs.getString("password");
            Long id = rs.getLong("id");
            return new CommonUserDetail(id ,pseudo, password, true, true, true, true, new ArrayList<>());
        });

        if (users.isEmpty()) {
            throw new BadCredentialsException(CommonUserConstants.USER_NOT_FOUND);
        }

        var user = users.get(0);

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(CommonUserConstants.PASSWORD_NOT_CORRECT);
        }

        return users.get(0);
    }
}

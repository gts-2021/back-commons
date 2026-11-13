package com.gts.backcommons.jwtauth;

import com.gts.backcommons.ssi.repositories.CommonUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest(
        classes = SecurityConfigContextTest.TestApp.class,
        properties = {
                "security.jwt.secret-key=01234567890123456789012345678901"
        }
)
class SecurityConfigContextTest {

    @SpringBootConfiguration
    @EnableAutoConfiguration(exclude = {
            DataSourceAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class,
            DataJpaRepositoriesAutoConfiguration.class
    })
    @ComponentScan(basePackageClasses = SecurityConfig.class)
    static class TestApp {
        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        }

        @Bean
        CommonUserRepository commonUserRepository() {
            return mock(CommonUserRepository.class);
        }

        @Component
        @RequiredArgsConstructor
        static class CustomClientFilter extends OncePerRequestFilter {
            private final AuthService authService;

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
                authService.checkRequestAuthentification(request, response, filterChain, dto ->
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), null, List.of()));
            }
        }

        @Configuration
        @EnableWebSecurity
        @EnableMethodSecurity(prePostEnabled = true)
        @RequiredArgsConstructor
        static class CustomClientSecurityConfig {
            private final CustomClientFilter customClientFilter;

            @Bean
            @Order(2)
            SecurityFilterChain customClientFilterChain(HttpSecurity http) throws Exception {
                return http
                        .securityMatcher("/public/**")
                        .addFilterBefore(customClientFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
            }
        }

        @Configuration
        static class TestFilterRegistration {
            @Bean
            FilterRegistrationBean<JwtAuthentificationFilter> userJwtFilterRegistration(JwtAuthentificationFilter filter) {
                var registration = new FilterRegistrationBean<>(filter);
                registration.setEnabled(false);
                return registration;
            }
        }
    }

    @Test
    void contextLoadsWithoutCircularDependency(ApplicationContext context) {
        assertNotNull(context.getBean("securityFilterChain", SecurityFilterChain.class));
        assertNotNull(context.getBean("customClientFilterChain", SecurityFilterChain.class));
        assertNotNull(context.getBean(AuthenticationManager.class));
        assertNotNull(context.getBean(AuthService.class));
        assertNotNull(context.getBean(JwtAuthentificationFilter.class));
        assertNotNull(context.getBean(AuthController.class));
    }
}

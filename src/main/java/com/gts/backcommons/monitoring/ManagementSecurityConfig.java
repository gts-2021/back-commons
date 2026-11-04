package com.gts.backcommons.monitoring;

import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for Spring Boot Actuator endpoints.
 * <p>
 * Evaluated with high precedence (@Order(1)) so actuator endpoints served on the dedicated
 * management port (or under /actuator/**) are permitted without JWT authentication.
 * Non-exposed endpoints (e.g. /actuator/env) correctly return 404 Not Found instead of 403 Forbidden.
 */
@Configuration
@NullMarked
public class ManagementSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain managementSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/actuator/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}


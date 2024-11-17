package com.gts.backcommons.jwtauth;


import com.gts.backcommons.models.CommonUser;
import com.gts.backcommons.ssi.SsiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfiguration {

    private final SsiConfiguration ssiConfiguration;

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery(
                "SELECT pseudo AS username, password, TRUE AS enabled FROM common_user WHERE pseudo = ?"
        );

        userDetailsManager.setAuthoritiesByUsernameQuery(
                "select pseudo AS username, cr.title as role from common_user cu \n" +
                        "inner join common_role cr on cr.id = cu.role_id  WHERE pseudo = ?"
        );

        return userDetailsManager;
    }


  /*@Bean
    public UserDetailsService userDetailsService() {

        return new InMemoryUserDetailsManager(ssiConfiguration.getAllUsers()
                .stream()
                .map(this::mapUserToUserDetail)
                .toList());
    }*/

    private UserDetails mapUserToUserDetail(CommonUser commonUser) {
        return User.builder()
                .username(commonUser.getPseudo())
                .password(commonUser.getPassword())
                .build();
    }
}

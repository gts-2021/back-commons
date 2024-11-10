package com.gts.backcommons.jwtauth;


import com.gts.backcommons.models.CommonUser;
import com.gts.backcommons.ssi.SsiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfiguration {

    private final SsiConfiguration ssiConfiguration;

    @Bean
    public UserDetailsService userDetailsService() {

        return new InMemoryUserDetailsManager(ssiConfiguration.getAllUsers()
                .stream()
                .map(this::mapUserToUserDetail)
                .toList());
    }

    private UserDetails mapUserToUserDetail(CommonUser commonUser) {
        return User.builder()
                .username(commonUser.getPseudo())
                .password(commonUser.getPassword())
                .build();
    }
}

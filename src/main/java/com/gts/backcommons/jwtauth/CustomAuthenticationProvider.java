package com.gts.backcommons.jwtauth;

import com.gts.backcommons.ssi.constants.CommonUserConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final DataSource dataSource;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        var companyCode = (String) authentication.getCredentials();

        var userDetailsManager = new JdbcUserDetailsManager(dataSource);

        var query = "SELECT pseudo AS username, password, TRUE AS enabled " +
                "FROM common_user " +
                "WHERE pseudo = ? AND company_id = (SELECT id FROM company WHERE code = ?)";

        var users = userDetailsManager.getJdbcTemplate().query(query, new Object[]{username, companyCode}, (rs, rowNum) -> {
            String password = rs.getString("password");
            return new User(username, password, true, true, true, true, new ArrayList<>());
        });

        if (users.isEmpty()) {
            throw new BadCredentialsException(CommonUserConstants.USER_NOT_FOUND);
        }

        return users.get(0);
    }
}

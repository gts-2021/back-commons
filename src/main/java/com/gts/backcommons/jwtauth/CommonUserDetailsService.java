package com.gts.backcommons.jwtauth;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonUserDetailsService implements UserDetailsService {

    private final DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var jdbcTemplate = new JdbcTemplate(dataSource);

        String query = "SELECT id, pseudo AS username, password, TRUE AS enabled FROM common_user WHERE pseudo = ?";
        List<CommonUserDetail> users = jdbcTemplate.query(query, new Object[]{username}, (rs, rowNum) -> {
            String password = rs.getString("password");
            Long id = rs.getLong("id");
            String pseudo = rs.getString("username");
            return new CommonUserDetail(id, pseudo, password, true, true, true, true, new ArrayList<>());
        });

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return users.get(0);
    }
}

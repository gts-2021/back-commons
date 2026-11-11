package com.gts.backcommons.jwtauth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CommonUserDetailsService userDetailsService;
    private final AuthService authService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

         authService.checkRequestAuthentification(request, response, filterChain, (dto) -> {
             var companyCode = jwtUtils.extractCompanyCode(dto.getToken());
             UserDetails userDetails = userDetailsService.loadUserByPseudoAndCompanyCode(dto.getUsername(), companyCode);

             UserAccessPolicy.requireActive(userDetails);
             return new UsernamePasswordAuthenticationToken(
                     userDetails, null, List.of()
             );
         });

    }


}
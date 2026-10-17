package com.gts.backcommons.jwtauth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gts.backcommons.exceptions.ErrorDetails;
import com.gts.backcommons.exceptions.InactiveUserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.gts.backcommons.exceptions.constants.ExceptionConstant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CommonUserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String authHeader = request.getHeader(Constants.HEADER_STRING);
        String pseudo = null;
        String token = null;


        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        try {
            if (authHeader != null && authHeader.startsWith(Constants.TOKEN_PREFIX)) {
                token = authHeader.replace(Constants.TOKEN_PREFIX, "");
                pseudo = jwtUtils.extractUsername(token);
            }

            if (pseudo != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtUtils.validateToken(token)) {
                var companyCode = jwtUtils.extractCompanyCode(token);
                UserDetails userDetails = userDetailsService.loadUserByPseudoAndCompanyCode(pseudo, companyCode);

                UserAccessPolicy.requireActive(userDetails);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, List.of()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


            filterChain.doFilter(request, response);

        } catch (InactiveUserException e) {
            SecurityContextHolder.clearContext();
            sendJwtError(response, InactiveUserException.ERROR_CODE, e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            sendJwtError(response, ExceptionConstant.INVALID_TOKEN, ExceptionConstant.USER_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            sendJwtError(response, ExceptionConstant.TOKEN_EXPIRED, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            sendJwtError(response, ExceptionConstant.INVALID_SIGNATURE, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            sendJwtError(response, ExceptionConstant.MALFORMED_TOKEN, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            sendJwtError(response, ExceptionConstant.INVALID_TOKEN, e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private void sendJwtError(HttpServletResponse response, String errorCode, String message, HttpStatus status) throws IOException {

        final ErrorDetails details = ErrorDetails.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(message)
                .errorCode(errorCode)
                .path(JwtAuthentificationFilter.class.getName())
                .build();
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(details));
    }

}
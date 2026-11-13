package com.gts.backcommons.jwtauth;

import com.gts.backcommons.exceptions.ErrorDetails;
import com.gts.backcommons.exceptions.InactiveUserException;
import com.gts.backcommons.exceptions.ResourceNotFoundException;
import com.gts.backcommons.exceptions.constants.ExceptionConstant;
import com.gts.backcommons.ssi.dtos.CommonRoleDTO;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import com.gts.backcommons.ssi.dtos.UserResponse;
import com.gts.backcommons.ssi.mapper.CommonRoleMapper;
import com.gts.backcommons.ssi.repositories.CommonUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final CommonUserRepository commonUserRepository;

    CommonRoleMapper roleMapper = Mappers.getMapper(CommonRoleMapper.class);

    public UserResponse authenticateAndGenerateToken(UserLoginDTO userLoginDTO) {

        var pseudo = userLoginDTO.getPseudo();
        var companyCode = userLoginDTO.getCompanyCode();

        var authToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPseudo(),
                userLoginDTO
        );
        authToken.setDetails(userLoginDTO.getCompanyCode());

        var authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var accessToken = jwtUtils.generateAccessToken(pseudo, companyCode);
        var refreshToken = jwtUtils.generateRefreshToken(pseudo, companyCode);

        var existingUser = commonUserRepository.findByPseudoAndCompanyCode(
                userLoginDTO.getPseudo(),
                userLoginDTO.getCompanyCode()
        ).orElseThrow(() -> new ResourceNotFoundException("CommonUser", "pseudo", userLoginDTO.getPseudo()));

        var role =  existingUser.getRole();

        var roleDto = roleMapper.toDto(role);


        return UserResponse.builder()
                .id(existingUser.getId())
                .pseudo(existingUser.getPseudo())
                .companyCode(userLoginDTO.getCompanyCode())
                .familyName(existingUser.getFamilyName())
                .firstName(existingUser.getFirstName())
                .email(existingUser.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(roleDto)
                .build();
    }

    @SneakyThrows
    public void checkRequestAuthentification(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Function<CheckRequestAuthDTO, UsernamePasswordAuthenticationToken> setupAuth) {

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
                var authToken = setupAuth.apply(CheckRequestAuthDTO.builder()
                                .token(token)
                                .username(pseudo)
                        .build());
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


    @SneakyThrows
    public void sendJwtError(HttpServletResponse response, String errorCode, String message, HttpStatus status) {
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

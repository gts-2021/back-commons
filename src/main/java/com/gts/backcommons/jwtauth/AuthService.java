package com.gts.backcommons.jwtauth;

import com.gts.backcommons.exceptions.ResourceNotFoundException;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import com.gts.backcommons.ssi.dtos.UserResponse;
import com.gts.backcommons.ssi.repositories.CommonUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final CommonUserRepository commonUserRepository;

    public UserResponse authenticateAndGenerateToken(UserLoginDTO userLoginDTO) {

        var authToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPseudo(),
                userLoginDTO.getCompanyCode()
        );
        authToken.setDetails(userLoginDTO.getCompanyCode());

        var authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtUtils.generateToken(userLoginDTO.getPseudo());

        var user = commonUserRepository.findByPseudoAndCompanyCode(
                userLoginDTO.getPseudo(),
                userLoginDTO.getCompanyCode()
        ).orElseThrow(() -> new ResourceNotFoundException("CommonUser", "pseudo", userLoginDTO.getPseudo()));

        return UserResponse.builder()
                .pseudo(user.getPseudo())
                .companyCode(userLoginDTO.getCompanyCode())
                .familyName(user.getFamilyName())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}

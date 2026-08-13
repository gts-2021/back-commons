package com.gts.backcommons.jwtauth;

import com.gts.backcommons.exceptions.ResourceNotFoundException;
import com.gts.backcommons.ssi.dtos.CommonRoleDTO;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import com.gts.backcommons.ssi.dtos.UserResponse;
import com.gts.backcommons.ssi.mapper.CommonRoleMapper;
import com.gts.backcommons.ssi.repositories.CommonUserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}

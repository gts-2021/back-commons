package com.gts.backcommons.jwtauth;


import com.gts.backcommons.models.CommonUser;
import com.gts.backcommons.ssi.constants.UserConstants;
import com.gts.backcommons.ssi.dtos.UserDTO;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import com.gts.backcommons.ssi.repositories.CommonUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final CommonUserRepository commonUserRepository;

    @PostMapping
    public UserDTO login(@RequestBody UserLoginDTO userLoginDTO){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getPseudo(), userLoginDTO.getPassword()));

        final String token = jwtUtils.generateToken(userLoginDTO.getPseudo());

        final CommonUser user = commonUserRepository.findByPseudo(
                userLoginDTO.getPseudo()).orElseThrow(() ->
                new RuntimeException(UserConstants.USER_NOT_FOUND));

         return UserDTO.builder()
                 .pseudo(user.getPseudo())
                 .familyName(user.getFamilyName())
                 .firstName(user.getFirstName())
                 .email(user.getEmail())
                 .token(token)
                 .build();

    }
}

package com.gts.backcommons.jwtauth;

import com.gts.backcommons.ssi.dtos.RefreshTokenDTO;
import com.gts.backcommons.ssi.dtos.UserResponse;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){

        final UserResponse userResult =  authService.authenticateAndGenerateToken(userLoginDTO);

        return ResponseEntity.ok(userResult);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {

        final String refreshToken = refreshTokenDTO.getRefreshToken();

        if (!jwtUtils.validateToken(refreshToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        final String pseudo = jwtUtils.extractUsername(refreshToken);
        final String companyCode = jwtUtils.extractCompanyCode(refreshToken);
        final String newAccessToken = jwtUtils.generateAccessToken(pseudo, companyCode);

        return ResponseEntity.ok(Map.of(Constants.ACCESS_TOKEN, newAccessToken));
    }
}

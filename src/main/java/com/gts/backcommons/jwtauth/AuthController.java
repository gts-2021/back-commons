package com.gts.backcommons.jwtauth;

import com.gts.backcommons.ssi.dtos.UserResponse;
import com.gts.backcommons.ssi.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){

        final UserResponse userResult =  authService.authenticateAndGenerateToken(userLoginDTO);

        return ResponseEntity.ok(userResult);
    }
}

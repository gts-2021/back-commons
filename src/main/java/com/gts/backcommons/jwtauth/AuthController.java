package com.gts.backcommons.jwtauth;


import com.gts.backcommons.ssi.dtos.UserLoginDTO;
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



    @PostMapping
    public String login(@RequestBody UserLoginDTO user){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getPseudo(),user.getPassword()));
        return jwtUtils.generateToken(user.getPseudo());

    }
}

package com.gts.backcommons.jwtauth;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class CheckRequestAuthDTO {

    private String token;
    private String username;
    private String password;
}

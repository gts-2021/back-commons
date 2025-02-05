package com.gts.backcommons.ssi.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String pseudo;
    private String firstName;
    private String familyName;
    private String email;
    private String token;
    private String companyCode;
}

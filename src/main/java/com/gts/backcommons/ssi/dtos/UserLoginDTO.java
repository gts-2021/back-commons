package com.gts.backcommons.ssi.dtos;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserLoginDTO {

    private String pseudo;
    private String password;
    private String companyCode;
}

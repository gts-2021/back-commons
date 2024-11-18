package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.ssi.constants.UserConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {

    @NotBlank(message = UserConstants.PSEUDO_REQUIRED)
    private String pseudo;
    @NotBlank(message = UserConstants.PASSWORD_REQUIRED)
    private String password;
    @NotBlank(message = UserConstants.COMPANY_CODE_REQUIRED)
    private String companyCode;
}

package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.ssi.constants.CommonUserConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {

    @NotBlank(message = CommonUserConstants.PSEUDO_REQUIRED)
    private String pseudo;
    @NotBlank(message = CommonUserConstants.PASSWORD_REQUIRED)
    private String password;
    @NotBlank(message = CommonUserConstants.COMPANY_CODE_REQUIRED)
    private String companyCode;
}

package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.ssi.constants.CommonUserConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = CommonUserConstants.PSEUDO_REQUIRED)
    private String pseudo;
    @NotBlank(message = CommonUserConstants.PASSWORD_REQUIRED)
    private String password;
    @NotBlank(message = CommonUserConstants.COMPANY_CODE_REQUIRED)
    private String companyCode;
}

package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.ssi.constants.UserConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CommonUserDTO {

    @NotBlank(message = UserConstants.FAMILY_NAME_ERROR)
    private String familyName;

    @NotBlank(message = UserConstants.FIRST_NAME_ERROR)
    private String firstName;

    @NotBlank(message = UserConstants.PSEUDO_ERROR)
    private String pseudo;

    @NotBlank(message = UserConstants.PASSWORD_ERROR)
    private String password;

}

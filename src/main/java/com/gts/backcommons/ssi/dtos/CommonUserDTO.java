package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.ssi.constants.UserConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String email;

    @NotBlank(message = UserConstants.PASSWORD_ERROR)
    private String password;

    @NotNull(message = UserConstants.ROLE_ERROR)
    private CommonRoleDTO role;

}

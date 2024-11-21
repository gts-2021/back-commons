package com.gts.backcommons.ssi.dtos;

import com.gts.backcommons.ssi.constants.CommonUserConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class CommonUserDTO {

    @NotBlank(message = CommonUserConstants.FAMILY_NAME_ERROR)
    private String familyName;

    @NotBlank(message = CommonUserConstants.FIRST_NAME_ERROR)
    private String firstName;

    @NotBlank(message = CommonUserConstants.PSEUDO_ERROR)
    private String pseudo;

    private String email;

    @NotBlank(message = CommonUserConstants.PASSWORD_ERROR)
    private String password;

    @NotNull(message = CommonUserConstants.ROLE_ERROR)
    private CommonRoleDTO role;

}

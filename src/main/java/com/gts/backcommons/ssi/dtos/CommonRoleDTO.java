package com.gts.backcommons.ssi.dtos;



import com.gts.backcommons.ssi.constants.RoleConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
public class CommonRoleDTO {

    private Long id;

    @NotEmpty(message = RoleConstants.TITLE_ERROR)
    private String title;
    private String description;


    @Size(min = 1, message = RoleConstants.MODULE_SIZE_ERROR)
    private List<CommonModuleDTO> modules;
    private List<CommonFunctionalityDTO> excludedFunctionalities;
}

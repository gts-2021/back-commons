package com.gts.backcommons.ssi.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class CommonModuleDTO {

    private Long id;
    private String title;
    private String description;

    private List<CommonFunctionalityDTO> functionalities;
}

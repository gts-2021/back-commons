package com.gts.backcommons.ssi.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class CommonFunctionalityDTO {

    private Long id;
    private String code;
    private String description;
}

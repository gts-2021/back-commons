package com.gts.backcommons.ssi.dtos;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CommonFunctionalityDTO {

    private Long id;
    private String code;
    private String description;
}

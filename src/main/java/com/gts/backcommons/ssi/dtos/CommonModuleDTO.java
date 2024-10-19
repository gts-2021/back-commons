package com.gts.backcommons.ssi.dtos;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CommonModuleDTO {

    private Long id;
    private String title;
    private String description;
}

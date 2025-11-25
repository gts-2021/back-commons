package com.gts.backcommons.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@MappedSuperclass
public class Reference extends TranslationBaseEntity {

    private String label;
    private String description;
}

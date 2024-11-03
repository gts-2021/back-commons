package com.gts.backcommons.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public class TranslationBaseEntity extends BaseEntity {

    @Column(columnDefinition = "TEXT") // it must contain json data
    private String translations;
}

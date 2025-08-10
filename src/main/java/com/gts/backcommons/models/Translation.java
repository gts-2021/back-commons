package com.gts.backcommons.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class Translation extends BaseEntity {

    String code;
    String label;
    String description;
}

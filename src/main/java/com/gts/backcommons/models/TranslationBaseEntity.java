package com.gts.backcommons.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public class TranslationBaseEntity extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    List<Translation> translations;

}

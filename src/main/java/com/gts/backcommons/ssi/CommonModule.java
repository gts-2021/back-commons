package com.gts.backcommons.ssi;

import com.gts.backcommons.models.BaseEntity;
import com.gts.backcommons.models.TranslationBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class CommonModule extends TranslationBaseEntity {

    private String title;
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.PERSIST)
    private List<CommonFunctionality> functionalities;

    @ManyToMany(mappedBy = "modules")
    private List<CommonRole> roles;
}

package com.gts.backcommons.ssi;

import com.gts.backcommons.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class CommonModule extends BaseEntity {

    private String title;
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.PERSIST)
    private List<CommonFunctionality> functionalities;

    @ManyToMany
    @JoinTable(
            name = "role_module",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "module_id") } )
    private List<CommonRole> roles;
}

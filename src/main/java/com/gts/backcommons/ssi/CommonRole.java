package com.gts.backcommons.ssi;
import com.gts.backcommons.models.BaseEntity;
import com.gts.backcommons.models.CommonUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class CommonRole extends BaseEntity {

    private String title;
    private String description;


    @OneToMany(mappedBy = "role")
    private List<CommonUser> users;

    @ManyToMany
    @JoinTable(
            name = "role_module",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "module_id") } )
    private List<CommonModule> modules;

    @ManyToMany
    @JoinTable(
            name = "excluded_role_functionality",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "functionality_id") } )
    private List<CommonFunctionality> excludedFunctionalities;
}

package com.gts.backcommons.ssi;

import com.gts.backcommons.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;


@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class CommonFunctionality extends BaseEntity {

    private String code;
    private String description;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private CommonModule module;

    @ManyToMany
    @JoinTable(
            name = "excluded_role_functionality",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "functionality_id") } )
    private List<CommonRole> excludedRoles;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommonFunctionality that = (CommonFunctionality) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code);
    }
}

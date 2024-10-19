package com.gts.backcommons.models;

import com.gts.backcommons.ssi.CommonRole;
import com.gts.backcommons.ssi.CommonFunctionality;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
public class CommonUser extends BaseEntity {



    private String familyName;
    private String firstName;
    private String pseudo;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private CommonRole role;


    public List<CommonFunctionality> getUserFunctionalities() {
        var excludedFunctionalities = role.getExcludedFunctionalities();
        return role.getModules()
                .stream()
                .flatMap(module -> module.getFunctionalities().stream())
                .filter(functionality -> !excludedFunctionalities.contains(functionality))
                .toList();
    }
}

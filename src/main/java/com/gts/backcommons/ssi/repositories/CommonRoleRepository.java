package com.gts.backcommons.ssi.repositories;

import com.gts.backcommons.ssi.CommonModule;
import com.gts.backcommons.ssi.CommonRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonRoleRepository extends JpaRepository<CommonRole, Long> {
    Optional<CommonRole> findByTitle(String title);
}

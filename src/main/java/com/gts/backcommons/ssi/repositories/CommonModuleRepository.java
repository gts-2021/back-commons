package com.gts.backcommons.ssi.repositories;

import com.gts.backcommons.ssi.CommonModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonModuleRepository extends JpaRepository<CommonModule,Long> {
    Optional<CommonModule> findByTitle(String title);
}

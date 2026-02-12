package com.gts.backcommons.ssi.repositories;

import com.gts.backcommons.models.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommonUserRepository extends JpaRepository<CommonUser,Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.pseudo) = LOWER(:pseudo) AND LOWER(u.company.code) = LOWER(:companyCode)")
    Optional<CommonUser> findByPseudoAndCompanyCode(@Param("pseudo") String pseudo, @Param("companyCode") String companyCode);
}


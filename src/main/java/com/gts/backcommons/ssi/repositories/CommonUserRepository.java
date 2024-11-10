package com.gts.backcommons.ssi.repositories;

import com.gts.backcommons.models.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommonUserRepository extends JpaRepository<CommonUser,Long> {

    Optional<CommonUser> findByPseudo(String pseudo);
}


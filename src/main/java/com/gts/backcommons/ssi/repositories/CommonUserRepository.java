package com.gts.backcommons.ssi.repositories;

import com.gts.backcommons.models.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommonUserRepository extends JpaRepository<CommonUser,Long> {
}


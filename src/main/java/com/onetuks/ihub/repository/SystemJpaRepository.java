package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.system.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemJpaRepository extends JpaRepository<System, Long> {
}

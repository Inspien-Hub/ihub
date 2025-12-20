package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceStatusJpaRepository extends JpaRepository<InterfaceStatus, Long> {
}

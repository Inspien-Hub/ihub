package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.interfaces.InterfaceRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceRevisionJpaRepository extends JpaRepository<InterfaceRevision, Long> {
}

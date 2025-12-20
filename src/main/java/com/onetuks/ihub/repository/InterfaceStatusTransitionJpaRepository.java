package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceStatusTransitionJpaRepository
    extends JpaRepository<InterfaceStatusTransition, Long> {
}

package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.interfaces.InterfaceStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceStatusHistoryJpaRepository
    extends JpaRepository<InterfaceStatusHistory, Long> {
}

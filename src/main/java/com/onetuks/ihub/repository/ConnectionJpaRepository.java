package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.system.Connection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionJpaRepository extends JpaRepository<Connection, String> {

  Page<Connection> findAllBySystem_SystemId(String systemId, Pageable pageable);
}

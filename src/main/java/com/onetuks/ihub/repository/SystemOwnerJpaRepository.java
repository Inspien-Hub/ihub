package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.system.SystemOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemOwnerJpaRepository extends JpaRepository<SystemOwner, String> {

  Page<SystemOwner> findAllByProject_ProjectId(String projectId, Pageable pageable);

  Page<SystemOwner> findAllByProject_ProjectIdAndUser_UserId(
      String projectId, String userId, Pageable pageable);
}

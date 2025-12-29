package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMember, String> {

  boolean existsByProject_ProjectIdAndUser(String projectId, User user);

  Page<ProjectMember> findAllByUser(User user, Pageable pageable);
}

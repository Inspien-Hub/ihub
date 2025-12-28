package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMember, String> {

  boolean existsByProjectAndUser(Project project, User user);
}

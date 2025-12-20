package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.project.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMember, Long> {
}

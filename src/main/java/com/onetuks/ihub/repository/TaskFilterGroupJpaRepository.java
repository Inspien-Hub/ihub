package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.task.TaskFilterGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskFilterGroupJpaRepository extends JpaRepository<TaskFilterGroup, Long> {
}

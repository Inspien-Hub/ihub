package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.task.TaskFilterGroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskFilterGroupStatusJpaRepository
    extends JpaRepository<TaskFilterGroupStatus, Long> {
}

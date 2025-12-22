package com.onetuks.ihub.service.task;

import com.onetuks.ihub.dto.task.TaskFilterGroupStatusCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusUpdateRequest;
import com.onetuks.ihub.entity.task.TaskFilterGroup;
import com.onetuks.ihub.entity.task.TaskFilterGroupStatus;
import com.onetuks.ihub.mapper.TaskFilterGroupStatusMapper;
import com.onetuks.ihub.repository.TaskFilterGroupJpaRepository;
import com.onetuks.ihub.repository.TaskFilterGroupStatusJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskFilterGroupStatusService {

  private final TaskFilterGroupStatusJpaRepository taskFilterGroupStatusJpaRepository;
  private final TaskFilterGroupJpaRepository taskFilterGroupJpaRepository;

  @Transactional
  public TaskFilterGroupStatus create(TaskFilterGroupStatusCreateRequest request) {
    TaskFilterGroupStatus status = new TaskFilterGroupStatus();
    TaskFilterGroupStatusMapper.applyCreate(status, request);
    status.setGroup(findGroup(request.groupId()));
    return taskFilterGroupStatusJpaRepository.save(status);
  }

  @Transactional(readOnly = true)
  public TaskFilterGroupStatus getById(Long statusId) {
    return findEntity(statusId);
  }

  @Transactional(readOnly = true)
  public List<TaskFilterGroupStatus> getAll() {
    return taskFilterGroupStatusJpaRepository.findAll();
  }

  @Transactional
  public TaskFilterGroupStatus update(
      Long statusId, TaskFilterGroupStatusUpdateRequest request) {
    TaskFilterGroupStatus status = findEntity(statusId);
    TaskFilterGroupStatusMapper.applyUpdate(status, request);
    return status;
  }

  @Transactional
  public void delete(Long statusId) {
    TaskFilterGroupStatus status = findEntity(statusId);
    taskFilterGroupStatusJpaRepository.delete(status);
  }

  private TaskFilterGroupStatus findEntity(Long statusId) {
    return taskFilterGroupStatusJpaRepository.findById(statusId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Task filter group status not found: " + statusId));
  }

  private TaskFilterGroup findGroup(Long groupId) {
    return taskFilterGroupJpaRepository.findById(groupId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Task filter group not found: " + groupId));
  }
}

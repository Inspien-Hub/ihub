package com.onetuks.ihub.service.task;

import com.onetuks.ihub.dto.task.TaskFilterGroupCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.task.TaskFilterGroup;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.TaskFilterGroupMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.TaskFilterGroupJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskFilterGroupService {

  private final TaskFilterGroupJpaRepository taskFilterGroupJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public TaskFilterGroup create(TaskFilterGroupCreateRequest request) {
    TaskFilterGroup group = new TaskFilterGroup();
    TaskFilterGroupMapper.applyCreate(group, request);
    group.setUser(findUser(request.userId()));
    group.setProject(findProject(request.projectId()));
    return taskFilterGroupJpaRepository.save(group);
  }

  @Transactional(readOnly = true)
  public TaskFilterGroup getById(Long groupId) {
    return findEntity(groupId);
  }

  @Transactional(readOnly = true)
  public List<TaskFilterGroup> getAll() {
    return taskFilterGroupJpaRepository.findAll();
  }

  @Transactional
  public TaskFilterGroup update(Long groupId, TaskFilterGroupUpdateRequest request) {
    TaskFilterGroup group = findEntity(groupId);
    TaskFilterGroupMapper.applyUpdate(group, request);
    return group;
  }

  @Transactional
  public void delete(Long groupId) {
    TaskFilterGroup group = findEntity(groupId);
    taskFilterGroupJpaRepository.delete(group);
  }

  private TaskFilterGroup findEntity(Long groupId) {
    return taskFilterGroupJpaRepository.findById(groupId)
        .orElseThrow(() -> new EntityNotFoundException("Task filter group not found: " + groupId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

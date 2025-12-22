package com.onetuks.ihub.service.task;

import com.onetuks.ihub.dto.task.TaskCreateRequest;
import com.onetuks.ihub.dto.task.TaskUpdateRequest;
import com.onetuks.ihub.entity.interfaces.Interface;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.task.Task;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.TaskMapper;
import com.onetuks.ihub.repository.InterfaceJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.TaskJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskJpaRepository taskJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final InterfaceJpaRepository interfaceJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Task create(TaskCreateRequest request) {
    Task task = new Task();
    TaskMapper.applyCreate(task, request);
    task.setProject(findProject(request.projectId()));
    if (request.parentTaskId() != null) {
      task.setParentTask(findTask(request.parentTaskId()));
    }
    if (request.interfaceId() != null) {
      task.setAnInterface(findInterface(request.interfaceId()));
    }
    if (request.assigneeId() != null) {
      task.setAssignee(findUser(request.assigneeId()));
    }
    if (request.requesterId() != null) {
      task.setRequester(findUser(request.requesterId()));
    }
    if (request.createdById() != null) {
      task.setCreatedBy(findUser(request.createdById()));
    }
    return taskJpaRepository.save(task);
  }

  @Transactional(readOnly = true)
  public Task getById(Long taskId) {
    return findEntity(taskId);
  }

  @Transactional(readOnly = true)
  public List<Task> getAll() {
    return taskJpaRepository.findAll();
  }

  @Transactional
  public Task update(Long taskId, TaskUpdateRequest request) {
    Task task = findEntity(taskId);
    TaskMapper.applyUpdate(task, request);
    if (request.parentTaskId() != null) {
      task.setParentTask(findTask(request.parentTaskId()));
    }
    if (request.interfaceId() != null) {
      task.setAnInterface(findInterface(request.interfaceId()));
    }
    if (request.assigneeId() != null) {
      task.setAssignee(findUser(request.assigneeId()));
    }
    if (request.requesterId() != null) {
      task.setRequester(findUser(request.requesterId()));
    }
    return task;
  }

  @Transactional
  public void delete(Long taskId) {
    Task task = findEntity(taskId);
    taskJpaRepository.delete(task);
  }

  private Task findEntity(Long taskId) {
    return taskJpaRepository.findById(taskId)
        .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));
  }

  private Task findTask(Long taskId) {
    return findEntity(taskId);
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private Interface findInterface(Long interfaceId) {
    return interfaceJpaRepository.findById(interfaceId)
        .orElseThrow(() -> new EntityNotFoundException("Interface not found: " + interfaceId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

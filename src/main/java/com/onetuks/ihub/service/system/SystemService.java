package com.onetuks.ihub.service.system;

import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemResponse;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.SystemMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemService {

  private final SystemJpaRepository systemJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public SystemResponse create(SystemCreateRequest request) {
    System system = new System();
    SystemMapper.applyCreate(system, request);
    system.setProject(findProject(request.projectId()));
    system.setCreatedBy(findUser(request.createdById()));
    system.setUpdatedBy(findUser(request.updatedById()));
    System saved = systemJpaRepository.save(system);
    return SystemMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public SystemResponse getById(Long systemId) {
    return SystemMapper.toResponse(findEntity(systemId));
  }

  @Transactional(readOnly = true)
  public List<SystemResponse> getAll() {
    return systemJpaRepository.findAll().stream()
        .map(SystemMapper::toResponse)
        .toList();
  }

  @Transactional
  public SystemResponse update(Long systemId, SystemUpdateRequest request) {
    System system = findEntity(systemId);
    SystemMapper.applyUpdate(system, request);
    if (request.updatedById() != null) {
      system.setUpdatedBy(findUser(request.updatedById()));
    }
    return SystemMapper.toResponse(system);
  }

  @Transactional
  public void delete(Long systemId) {
    System system = findEntity(systemId);
    systemJpaRepository.delete(system);
  }

  private System findEntity(Long systemId) {
    return systemJpaRepository.findById(systemId)
        .orElseThrow(() -> new EntityNotFoundException("System not found: " + systemId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

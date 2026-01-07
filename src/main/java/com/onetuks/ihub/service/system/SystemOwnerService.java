package com.onetuks.ihub.service.system;

import com.onetuks.ihub.dto.system.SystemOwnerCreateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.system.SystemOwner;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.SystemOwnerMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.repository.SystemOwnerJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemOwnerService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final SystemOwnerJpaRepository systemOwnerJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final SystemJpaRepository systemJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public SystemOwner create(User currentUser, String projectId, SystemOwnerCreateRequest request) {
    Project project = findProject(projectId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(), projectId);
    User owner = findUser(request.userId());
    projectMemberCheckComponent.checkIsProjectMember(owner.getUserId(), projectId);
    return systemOwnerJpaRepository.save(
        SystemOwnerMapper.applyCreate(project, findSystem(request.systemId()), owner));
  }

  @Transactional(readOnly = true)
  public Page<SystemOwner> getAll(
      User currentUser, String projectId, String userId, Pageable pageable) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);
    if (userId == null || userId.isBlank()) {
      return systemOwnerJpaRepository.findAllByProject_ProjectId(projectId, pageable);
    }
    return systemOwnerJpaRepository.findAllByProject_ProjectIdAndUser_UserId(
        projectId, userId, pageable);
  }

  @Transactional
  public void delete(User currentUser, String systemOwnerId) {
    SystemOwner systemOwner = findEntity(systemOwnerId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), systemOwner.getProject().getProjectId());
    systemOwnerJpaRepository.delete(systemOwner);
  }

  private SystemOwner findEntity(String systemOwnerId) {
    return systemOwnerJpaRepository.findById(systemOwnerId)
        .orElseThrow(() -> new EntityNotFoundException(
            "System owner not found: " + systemOwnerId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private System findSystem(String systemId) {
    return systemJpaRepository.findById(systemId)
        .orElseThrow(() -> new EntityNotFoundException("System not found: " + systemId));
  }

  private User findUser(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

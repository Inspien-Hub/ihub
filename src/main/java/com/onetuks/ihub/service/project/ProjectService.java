package com.onetuks.ihub.service.project;

import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.ProjectMapper;
import com.onetuks.ihub.mapper.ProjectMemberMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectJpaRepository projectRepository;
  private final ProjectMemberJpaRepository projectMemberRepository;
  private final UserJpaRepository userRepository;

  @Transactional
  public Project create(User currentUser, ProjectCreateRequest request) {
    Project project = projectRepository.save(
        ProjectMapper.applyCreate(request, currentUser, findUser(request.currentAdminId())));
    projectMemberRepository.save(ProjectMemberMapper.applyCreate(project, currentUser));
    return project;
  }

  @Transactional(readOnly = true)
  public Project getById(User currentUser, String projectId) {
    checkIsProjectMember(currentUser, projectId);
    return findEntity(projectId);
  }

  @Transactional(readOnly = true)
  public Page<Project> getAll(User currentUser, Pageable pageable) {
    return projectMemberRepository.findAllByUser(currentUser, pageable)
        .map(ProjectMember::getProject);
  }

  @Transactional
  public Project update(User currentUser, String projectId, ProjectUpdateRequest request) {
    checkIsProjectMember(currentUser, projectId);

    Project project = findEntity(projectId);
    ProjectMapper.applyUpdate(project, request);
    if (request.currentAdminId() != null) {
      checkIsProjectMember(findUser(request.currentAdminId()), projectId);
      project.setCurrentAdmin(findUser(request.currentAdminId()));
    }
    return project;
  }

  @Transactional
  public Project delete(User currentUser, String projectId) {
    checkIsProjectMember(currentUser, projectId);

    Project project = findEntity(projectId);
    project.setStatus(ProjectStatus.DELETED);
    return project;
  }

  private void checkIsProjectMember(User user, String projectId) {
    boolean isProjectMember = projectMemberRepository.existsByProject_ProjectIdAndUser(projectId, user);
    if (!isProjectMember) {
      throw new AccessDeniedException("프로젝트 멤버가 아닙니다.");
    }
  }

  private Project findEntity(String projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(String userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

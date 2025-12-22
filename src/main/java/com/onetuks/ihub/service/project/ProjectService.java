package com.onetuks.ihub.service.project;

import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.ProjectMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Project create(ProjectCreateRequest request) {
    Project project = new Project();
    ProjectMapper.applyCreate(project, request);
    project.setCreatedBy(findUser(request.createdById()));
    project.setCurrentAdmin(findUser(request.currentAdminId()));
    return projectJpaRepository.save(project);
  }

  @Transactional(readOnly = true)
  public Project getById(Long projectId) {
    return findEntity(projectId);
  }

  @Transactional(readOnly = true)
  public List<Project> getAll() {
    return projectJpaRepository.findAll();
  }

  @Transactional
  public Project update(Long projectId, ProjectUpdateRequest request) {
    Project project = findEntity(projectId);
    ProjectMapper.applyUpdate(project, request);
    if (request.currentAdminId() != null) {
      project.setCurrentAdmin(findUser(request.currentAdminId()));
    }
    return project;
  }

  @Transactional
  public void delete(Long projectId) {
    Project project = findEntity(projectId);
    projectJpaRepository.delete(project);
  }

  private Project findEntity(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

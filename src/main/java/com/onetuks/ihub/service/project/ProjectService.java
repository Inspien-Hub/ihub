package com.onetuks.ihub.service.project;

import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.FavoriteProject;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.ProjectMapper;
import com.onetuks.ihub.mapper.ProjectMemberMapper;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.FavoriteProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final ProjectJpaRepository projectRepository;
  private final ProjectMemberJpaRepository projectMemberRepository;
  private final FavoriteProjectJpaRepository favoriteProjectRepository;

  @Transactional
  public Project create(User currentUser, ProjectCreateRequest request) {
    Project project = projectRepository.save(ProjectMapper.applyCreate(request, currentUser));
    projectMemberRepository.save(ProjectMemberMapper.applyCreate(project, currentUser));
    return project;
  }

  @Transactional(readOnly = true)
  public Project getById(User currentUser, String projectId) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);
    return findEntity(projectId);
  }

  @Transactional(readOnly = true)
  public Page<Project> getAllMine(User currentUser, Pageable pageable) {
    return projectMemberRepository.findAllByUser(currentUser, pageable)
        .map(ProjectMember::getProject);
  }

  @Transactional(readOnly = true)
  public Page<Project> getAll(Pageable pageable) {
    return projectRepository.findAll(pageable);
  }

  @Transactional
  public Project update(User currentUser, String projectId, ProjectUpdateRequest request) {
    projectMemberCheckComponent.checkIsProjectOwner(currentUser.getUserId(), projectId);
    return ProjectMapper.applyUpdate(findEntity(projectId), currentUser, request);
  }

  @Transactional
  public Project delete(User currentUser, String projectId) {
    projectMemberCheckComponent.checkIsProjectOwner(currentUser.getUserId(), projectId);

    Project project = findEntity(projectId);
    project.setStatus(ProjectStatus.DELETED);
    return project;
  }

  @Transactional
  public FavoriteProject manageFavorite(User currentUser, String projectId) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);
    return favoriteProjectRepository.findByProject_ProjectIdAndUser_UserId(
            projectId, currentUser.getUserId())
        .map(favoriteProject -> {
          favoriteProject.setIsFavorite(!favoriteProject.getIsFavorite());
          return favoriteProject;
        })
        .orElseGet(() -> favoriteProjectRepository.save(
            new FavoriteProject(
                UUIDProvider.provideUUID(FavoriteProject.TABLE_NAME),
                findEntity(projectId),
                currentUser,
                true
            )));
  }

  @Transactional(readOnly = true)
  public boolean getFavorited(User currentUser, String projectId) {
    return favoriteProjectRepository.findByProject_ProjectIdAndUser_UserId(
            projectId, currentUser.getUserId())
        .map(FavoriteProject::getIsFavorite)
        .orElseGet(() -> false);
  }

  private Project findEntity(String projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

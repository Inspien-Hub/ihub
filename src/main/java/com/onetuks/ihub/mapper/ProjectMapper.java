package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.project.FavoriteProjectResponse;
import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectResponse;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.FavoriteProject;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ProjectMapper {

  private ProjectMapper() {
  }

  public static ProjectResponse toResponse(Project project) {
    var createdBy = Objects.requireNonNull(project.getCreatedBy());
    var updatedBy = Objects.requireNonNull(project.getUpdatedBy());
    return new ProjectResponse(
        project.getProjectId(),
        project.getTitle(),
        project.getDescription(),
        project.getStartDate(),
        project.getEndDate(),
        project.getStatus(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        updatedBy.getUserId(),
        updatedBy.getStatus(),
        updatedBy.getName(),
        project.getCreatedAt(),
        project.getUpdatedAt(),
        false);
  }

  public static ProjectResponse toResponse(Project project, boolean isFavorite) {
    var createdBy = Objects.requireNonNull(project.getCreatedBy());
    var updatedBy = Objects.requireNonNull(project.getUpdatedBy());
    return new ProjectResponse(
        project.getProjectId(),
        project.getTitle(),
        project.getDescription(),
        project.getStartDate(),
        project.getEndDate(),
        project.getStatus(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        updatedBy.getUserId(),
        updatedBy.getStatus(),
        updatedBy.getName(),
        project.getCreatedAt(),
        project.getUpdatedAt(),
        isFavorite);
  }

  public static FavoriteProjectResponse toResponse(FavoriteProject favoriteProject) {
    return new FavoriteProjectResponse(
        favoriteProject.getFavoriteProjectId(),
        favoriteProject.getIsFavorite()
    );
  }

  public static Project applyCreate(
      ProjectCreateRequest request, User createdBy) {
    LocalDateTime now = LocalDateTime.now();
    return new Project(
        UUIDProvider.provideUUID(Project.TABLE_NAME),
        request.title(),
        request.description(),
        request.startDate(),
        request.endDate(),
        createdBy,
        createdBy,
        Objects.requireNonNullElse(request.status(), ProjectStatus.ACTIVE),
        now,
        now
    );
  }

  public static Project applyUpdate(Project project, User currentUser,
      ProjectUpdateRequest request) {
    if (request.title() != null) {
      project.setTitle(request.title());
    }
    if (request.description() != null) {
      project.setDescription(request.description());
    }
    if (request.startDate() != null) {
      project.setStartDate(request.startDate());
    }
    if (request.endDate() != null) {
      project.setEndDate(request.endDate());
    }
    ProjectStatus status = request.status();
    if (status != null) {
      project.setStatus(status);
    }
    project.setUpdatedBy(currentUser);
    project.setUpdatedAt(LocalDateTime.now());
    return project;
  }
}

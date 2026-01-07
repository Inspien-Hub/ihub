package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ProjectMemberMapper {

  private ProjectMemberMapper() {
  }

  public static ProjectMemberResponse toResponse(ProjectMember projectMember) {
    var project = Objects.requireNonNull(projectMember.getProject());
    var user = Objects.requireNonNull(projectMember.getUser());
    return new ProjectMemberResponse(
        projectMember.getProjectMemberId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        user.getUserId(),
        user.getStatus(),
        user.getName(),
        user.getEmail(),
        projectMember.getRole(),
        projectMember.getJoinedAt(),
        projectMember.getLeftAt());
  }

  public static ProjectMember applyCreate(Project project, User currentUser) {
    return new ProjectMember(
        UUIDProvider.provideUUID(ProjectMember.TABLE_NAME),
        project,
        currentUser,
        ProjectMemberRole.PROJECT_VIEWER,
        LocalDateTime.now(),
        null);
  }

  public static ProjectMember applyCreate(Project project, User user, ProjectMemberRole role) {
    return new ProjectMember(
        UUIDProvider.provideUUID(ProjectMember.TABLE_NAME),
        project,
        user,
        role,
        LocalDateTime.now(),
        null);
  }

  public static ProjectMember applyUpdate(
      ProjectMember projectMember, ProjectMemberUpdateRequest request) {
    if (request.role() != null) {
      projectMember.setRole(request.role());
      if (request.role().equals(ProjectMemberRole.PROJECT_VIEWER)) {
        projectMember.setLeftAt(LocalDateTime.now());
      }
    }
    return projectMember;
  }
}

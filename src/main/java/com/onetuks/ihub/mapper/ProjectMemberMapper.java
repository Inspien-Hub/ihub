package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;

public final class ProjectMemberMapper {

  private ProjectMemberMapper() {
  }

  public static ProjectMemberResponse toResponse(ProjectMember projectMember) {
    return new ProjectMemberResponse(
        projectMember.getProjectMemberId(),
        projectMember.getProject() != null ? projectMember.getProject().getProjectId() : null,
        projectMember.getUser() != null ? projectMember.getUser().getUserId() : null,
        projectMember.getRole(),
        projectMember.getJoinedAt(),
        projectMember.getLeftAt());
  }

  public static ProjectMember applyCreate(Project project, User currentUser) {
    return new ProjectMember(
        UUIDProvider.provideUUID(ProjectMember.TABLE_NAME),
        project,
        currentUser,
        ProjectMemberRole.PROJECT_OWNER,
        LocalDateTime.now(),
        null);
  }
}

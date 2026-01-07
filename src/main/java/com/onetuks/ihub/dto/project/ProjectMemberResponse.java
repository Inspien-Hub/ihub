package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record ProjectMemberResponse(
    String projectMemberId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String userId,
    UserStatus userStatus,
    String userName,
    String userEmail,
    ProjectMemberRole role,
    LocalDateTime joinedAt,
    LocalDateTime leftAt
) {

}

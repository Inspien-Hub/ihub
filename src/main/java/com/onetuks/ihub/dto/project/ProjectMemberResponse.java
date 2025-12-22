package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;
import java.time.LocalDateTime;

public record ProjectMemberResponse(
    String projectMemberId,
    String projectId,
    String userId,
    ProjectMemberRole role,
    LocalDateTime joinedAt,
    LocalDateTime leftAt
) {
}

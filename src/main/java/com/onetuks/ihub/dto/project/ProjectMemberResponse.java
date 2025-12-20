package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;
import java.time.LocalDateTime;

public record ProjectMemberResponse(
    Long projectMemberId,
    Long projectId,
    Long userId,
    ProjectMemberRole role,
    LocalDateTime joinedAt,
    LocalDateTime leftAt
) {
}

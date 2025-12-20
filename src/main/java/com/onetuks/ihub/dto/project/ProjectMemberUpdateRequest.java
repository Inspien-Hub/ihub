package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;
import java.time.LocalDateTime;

public record ProjectMemberUpdateRequest(
    ProjectMemberRole role,
    LocalDateTime leftAt
) {
}

package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record SystemOwnerResponse(
    String systemOwnerId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String systemId,
    String systemCode,
    String userId,
    UserStatus userStatus,
    String userName,
    LocalDateTime createdAt
) {

}

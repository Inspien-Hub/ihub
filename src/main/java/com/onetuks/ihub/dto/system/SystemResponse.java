package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.system.SystemEnvironment;
import com.onetuks.ihub.entity.system.SystemStatus;
import com.onetuks.ihub.entity.system.SystemType;
import java.time.LocalDateTime;

public record SystemResponse(
    Long systemId,
    Long projectId,
    String systemCode,
    SystemStatus status,
    String description,
    SystemType systemType,
    SystemEnvironment environment,
    Long createdById,
    Long updatedById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

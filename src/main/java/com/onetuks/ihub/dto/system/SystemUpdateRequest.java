package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.system.SystemEnvironment;
import com.onetuks.ihub.entity.system.SystemStatus;
import com.onetuks.ihub.entity.system.SystemType;

public record SystemUpdateRequest(
    String systemCode,
    SystemStatus status,
    String description,
    SystemType systemType,
    SystemEnvironment environment,
    Long updatedById
) {
}

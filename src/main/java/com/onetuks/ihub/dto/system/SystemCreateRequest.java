package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.system.SystemEnvironment;
import com.onetuks.ihub.entity.system.SystemStatus;
import com.onetuks.ihub.entity.system.SystemType;
import jakarta.validation.constraints.NotNull;

public record SystemCreateRequest(
    @NotNull Long projectId,
    String systemCode,
    @NotNull SystemStatus status,
    String description,
    @NotNull SystemType systemType,
    @NotNull SystemEnvironment environment,
    @NotNull Long createdById,
    @NotNull Long updatedById
) {
}

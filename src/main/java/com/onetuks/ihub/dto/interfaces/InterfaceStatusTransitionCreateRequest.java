package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import jakarta.validation.constraints.NotNull;

public record InterfaceStatusTransitionCreateRequest(
    @NotNull Long projectId,
    @NotNull Long fromStatusId,
    @NotNull Long toStatusId,
    @NotNull InterfaceRole allowedRole,
    @NotNull InterfaceStatusTransitionStatus status,
    @NotNull Long createdById
) {
}

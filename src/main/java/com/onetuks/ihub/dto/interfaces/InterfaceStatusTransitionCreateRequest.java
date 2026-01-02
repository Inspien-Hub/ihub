package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import jakarta.validation.constraints.NotNull;

public record InterfaceStatusTransitionCreateRequest(
    @NotNull String projectId,
    @NotNull String fromStatusId,
    @NotNull String toStatusId,
    @NotNull InterfaceRole allowedRole
) {

}

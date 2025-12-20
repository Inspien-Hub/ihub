package com.onetuks.ihub.dto.interfaces;

import jakarta.validation.constraints.NotNull;

public record InterfaceStatusHistoryCreateRequest(
    @NotNull Long interfaceId,
    @NotNull Long fromStatusId,
    @NotNull Long toStatusId,
    @NotNull Long changedById,
    @NotNull Long relatedTaskId,
    String reason
) {
}

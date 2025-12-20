package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record AttachmentCreateRequest(
    @NotNull Long projectId,
    @NotNull Long fileId,
    @NotNull TargetType targetType,
    @NotNull Long targetId,
    @NotNull Long attachedById
) {
}

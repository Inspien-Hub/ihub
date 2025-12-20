package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record MentionCreateRequest(
    @NotNull Long projectId,
    @NotNull TargetType targetType,
    @NotNull Long targetId,
    @NotNull Long mentionedUserId,
    @NotNull Long createdById
) {
}

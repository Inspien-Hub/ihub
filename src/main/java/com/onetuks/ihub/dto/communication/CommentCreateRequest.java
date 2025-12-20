package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
    @NotNull Long projectId,
    Long parentCommentId,
    TargetType targetType,
    Long targetId,
    String content,
    Long createdById
) {
}

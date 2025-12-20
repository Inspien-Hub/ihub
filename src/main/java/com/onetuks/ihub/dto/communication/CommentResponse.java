package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record CommentResponse(
    Long commentId,
    Long projectId,
    Long parentCommentId,
    TargetType targetType,
    Long targetId,
    String content,
    Long createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

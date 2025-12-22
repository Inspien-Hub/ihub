package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record CommentResponse(
    String commentId,
    String projectId,
    String parentCommentId,
    TargetType targetType,
    String targetId,
    String content,
    String createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

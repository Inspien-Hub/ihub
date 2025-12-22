package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record MentionResponse(
    String mentionId,
    String projectId,
    TargetType targetType,
    String targetId,
    String mentionedUserId,
    String createdById,
    LocalDateTime createdAt
) {
}

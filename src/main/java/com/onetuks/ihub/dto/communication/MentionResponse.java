package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record MentionResponse(
    Long mentionId,
    Long projectId,
    TargetType targetType,
    Long targetId,
    Long mentionedUserId,
    Long createdById,
    LocalDateTime createdAt
) {
}

package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record FeedItemResponse(
    String feedId,
    String projectId,
    String eventType,
    String actorId,
    TargetType targetType,
    String targetId,
    String summary,
    LocalDateTime createdAt
) {
}

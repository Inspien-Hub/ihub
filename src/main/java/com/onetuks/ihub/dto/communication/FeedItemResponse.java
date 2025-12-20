package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record FeedItemResponse(
    Long feedId,
    Long projectId,
    String eventType,
    Long actorId,
    TargetType targetType,
    Long targetId,
    String summary,
    LocalDateTime createdAt
) {
}

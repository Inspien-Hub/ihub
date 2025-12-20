package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;

public record FeedItemUpdateRequest(
    String eventType,
    Long actorId,
    TargetType targetType,
    Long targetId,
    String summary
) {
}

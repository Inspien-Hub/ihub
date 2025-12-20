package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record FeedItemCreateRequest(
    @NotNull Long projectId,
    String eventType,
    Long actorId,
    TargetType targetType,
    Long targetId,
    String summary
) {
}

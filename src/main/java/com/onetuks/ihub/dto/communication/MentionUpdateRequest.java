package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;

public record MentionUpdateRequest(
    TargetType targetType,
    Long targetId,
    Long mentionedUserId,
    Long createdById
) {
}

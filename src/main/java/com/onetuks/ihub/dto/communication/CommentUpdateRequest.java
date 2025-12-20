package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;

public record CommentUpdateRequest(
    TargetType targetType,
    Long targetId,
    String content
) {
}

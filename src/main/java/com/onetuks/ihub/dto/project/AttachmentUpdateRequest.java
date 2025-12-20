package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;

public record AttachmentUpdateRequest(
    Long fileId,
    TargetType targetType,
    Long targetId,
    Long attachedById
) {
}

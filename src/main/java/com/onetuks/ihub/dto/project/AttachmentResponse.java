package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record AttachmentResponse(
    Long attachmentId,
    Long projectId,
    Long fileId,
    TargetType targetType,
    Long targetId,
    Long attachedById,
    LocalDateTime attachedAt
) {
}

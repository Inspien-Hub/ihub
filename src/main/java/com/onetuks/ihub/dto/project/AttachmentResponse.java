package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;
import java.time.LocalDateTime;

public record AttachmentResponse(
    String attachmentId,
    String projectId,
    String fileId,
    TargetType targetType,
    String targetId,
    String attachedById,
    LocalDateTime attachedAt
) {
}

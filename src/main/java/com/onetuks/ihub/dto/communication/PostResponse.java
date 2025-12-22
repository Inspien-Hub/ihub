package com.onetuks.ihub.dto.communication;

import java.time.LocalDateTime;

public record PostResponse(
    String postId,
    String projectId,
    String title,
    String content,
    String createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

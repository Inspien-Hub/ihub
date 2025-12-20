package com.onetuks.ihub.dto.communication;

import java.time.LocalDateTime;

public record PostResponse(
    Long postId,
    Long projectId,
    String title,
    String content,
    Long createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

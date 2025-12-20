package com.onetuks.ihub.dto.file;

import java.time.LocalDateTime;

public record FolderResponse(
    Long folderId,
    Long projectId,
    Long parentFolderId,
    String name,
    Long createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

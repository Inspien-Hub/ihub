package com.onetuks.ihub.dto.file;

import java.time.LocalDateTime;

public record FolderResponse(
    String folderId,
    String projectId,
    String parentFolderId,
    String name,
    String createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.file.FileStatus;
import java.time.LocalDateTime;

public record FileResponse(
    Long fileId,
    Long projectId,
    Long folderId,
    FileStatus status,
    String originalName,
    String storedName,
    Long sizeBytes,
    String mimeType,
    Long uploadedById,
    LocalDateTime uploadedAt,
    LocalDateTime deletedAt
) {
}

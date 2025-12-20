package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.file.FileStatus;
import java.time.LocalDateTime;

public record FileUpdateRequest(
    Long folderId,
    FileStatus status,
    String originalName,
    String storedName,
    Long sizeBytes,
    String mimeType,
    Long uploadedById,
    LocalDateTime deletedAt
) {
}

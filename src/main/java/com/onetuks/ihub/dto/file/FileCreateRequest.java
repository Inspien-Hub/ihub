package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.file.FileStatus;
import jakarta.validation.constraints.NotNull;

public record FileCreateRequest(
    @NotNull Long projectId,
    Long folderId,
    @NotNull FileStatus status,
    String originalName,
    String storedName,
    Long sizeBytes,
    String mimeType,
    Long uploadedById
) {
}

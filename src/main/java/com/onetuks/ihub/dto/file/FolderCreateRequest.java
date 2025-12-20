package com.onetuks.ihub.dto.file;

import jakarta.validation.constraints.NotNull;

public record FolderCreateRequest(
    @NotNull Long projectId,
    Long parentFolderId,
    String name,
    Long createdById
) {
}

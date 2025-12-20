package com.onetuks.ihub.dto.file;

public record FolderUpdateRequest(
    Long parentFolderId,
    String name
) {
}

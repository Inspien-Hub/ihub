package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
    @NotNull Long projectId,
    String title,
    String content,
    Long createdById
) {
}

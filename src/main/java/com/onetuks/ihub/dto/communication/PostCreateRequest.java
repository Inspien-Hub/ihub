package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
    @NotNull String projectId,
    String title,
    String content
) {

}

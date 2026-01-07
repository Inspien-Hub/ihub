package com.onetuks.ihub.dto.project;

import jakarta.validation.constraints.NotNull;

public record ProjectMemberCreateRequest(
    @NotNull String userId
) {

}

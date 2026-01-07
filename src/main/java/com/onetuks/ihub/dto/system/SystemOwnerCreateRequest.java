package com.onetuks.ihub.dto.system;

import jakarta.validation.constraints.NotNull;

public record SystemOwnerCreateRequest(
    @NotNull String systemId,
    @NotNull String userId
) {

}

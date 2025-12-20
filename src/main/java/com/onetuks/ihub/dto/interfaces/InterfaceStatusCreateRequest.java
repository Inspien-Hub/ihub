package com.onetuks.ihub.dto.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InterfaceStatusCreateRequest(
    @NotNull Long projectId,
    @NotBlank String name,
    String code,
    @NotNull Integer seqOrder,
    @NotNull Boolean isDefault
) {
}

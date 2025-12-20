package com.onetuks.ihub.dto.interfaces;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record InterfaceRevisionCreateRequest(
    @NotNull Long interfaceId,
    @NotNull Integer versionNo,
    @NotNull Long changedById,
    Map<String, String> snapshot,
    String reason
) {
}

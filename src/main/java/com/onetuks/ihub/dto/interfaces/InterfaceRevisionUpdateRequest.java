package com.onetuks.ihub.dto.interfaces;

import java.util.Map;

public record InterfaceRevisionUpdateRequest(
    Integer versionNo,
    Long changedById,
    Map<String, String> snapshot,
    String reason
) {
}

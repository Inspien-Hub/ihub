package com.onetuks.ihub.dto.interfaces;

import java.time.LocalDateTime;
import java.util.Map;

public record InterfaceRevisionResponse(
    String revisionId,
    String interfaceId,
    Integer versionNo,
    String changedById,
    LocalDateTime changedAt,
    Map<String, String> snapshot,
    String reason
) {
}

package com.onetuks.ihub.dto.interfaces;

import java.time.LocalDateTime;
import java.util.Map;

public record InterfaceRevisionResponse(
    Long revisionId,
    Long interfaceId,
    Integer versionNo,
    Long changedById,
    LocalDateTime changedAt,
    Map<String, String> snapshot,
    String reason
) {
}

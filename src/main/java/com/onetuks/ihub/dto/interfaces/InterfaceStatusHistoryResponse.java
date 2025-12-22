package com.onetuks.ihub.dto.interfaces;

import java.time.LocalDateTime;

public record InterfaceStatusHistoryResponse(
    String historyId,
    String interfaceId,
    String fromStatusId,
    String toStatusId,
    String changedById,
    LocalDateTime changedAt,
    String relatedTaskId,
    String reason
) {
}

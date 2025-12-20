package com.onetuks.ihub.dto.interfaces;

import java.time.LocalDateTime;

public record InterfaceStatusHistoryResponse(
    Long historyId,
    Long interfaceId,
    Long fromStatusId,
    Long toStatusId,
    Long changedById,
    LocalDateTime changedAt,
    Long relatedTaskId,
    String reason
) {
}

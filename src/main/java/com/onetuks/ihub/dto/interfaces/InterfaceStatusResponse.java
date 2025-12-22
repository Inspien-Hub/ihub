package com.onetuks.ihub.dto.interfaces;

import java.time.LocalDateTime;

public record InterfaceStatusResponse(
    String statusId,
    String projectId,
    String name,
    String code,
    Integer seqOrder,
    Boolean isDefault,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

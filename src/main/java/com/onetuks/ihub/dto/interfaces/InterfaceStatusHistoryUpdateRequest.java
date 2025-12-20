package com.onetuks.ihub.dto.interfaces;

public record InterfaceStatusHistoryUpdateRequest(
    Long toStatusId,
    String reason
) {
}

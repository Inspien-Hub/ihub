package com.onetuks.ihub.dto.interfaces;

public record InterfaceStatusUpdateRequest(
    String name,
    String code,
    Integer seqOrder,
    Boolean isDefault
) {
}

package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import java.time.LocalDateTime;

public record InterfaceStatusTransitionResponse(
    Long transitionId,
    Long projectId,
    Long fromStatusId,
    Long toStatusId,
    InterfaceRole allowedRole,
    InterfaceStatusTransitionStatus status,
    LocalDateTime createdAt,
    Long createdById
) {
}

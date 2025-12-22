package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import java.time.LocalDateTime;

public record InterfaceStatusTransitionResponse(
    String transitionId,
    String projectId,
    String fromStatusId,
    String toStatusId,
    InterfaceRole allowedRole,
    InterfaceStatusTransitionStatus status,
    LocalDateTime createdAt,
    String createdById
) {
}

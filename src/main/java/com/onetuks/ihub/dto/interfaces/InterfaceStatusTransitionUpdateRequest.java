package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;

public record InterfaceStatusTransitionUpdateRequest(
    Long fromStatusId,
    Long toStatusId,
    InterfaceRole allowedRole,
    InterfaceStatusTransitionStatus status
) {
}

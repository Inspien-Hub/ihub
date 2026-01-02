package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransition;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;

public final class InterfaceStatusTransitionMapper {

  private InterfaceStatusTransitionMapper() {
  }

  public static InterfaceStatusTransitionResponse toResponse(
      InterfaceStatusTransition transition) {
    return new InterfaceStatusTransitionResponse(
        transition.getTransitionId(),
        transition.getProject() != null ? transition.getProject().getProjectId() : null,
        transition.getFromStatus() != null ? transition.getFromStatus().getStatusId() : null,
        transition.getToStatus() != null ? transition.getToStatus().getStatusId() : null,
        transition.getAllowedRole(),
        transition.getStatus(),
        transition.getCreatedAt(),
        transition.getCreatedBy() != null ? transition.getCreatedBy().getUserId() : null);
  }

  public static InterfaceStatusTransition applyCreate(
      Project project,
      InterfaceStatus fromStatus,
      InterfaceStatus toStatus,
      User createdBy,
      InterfaceStatusTransitionCreateRequest request) {
    return new InterfaceStatusTransition(
        UUIDProvider.provideUUID(InterfaceStatusTransition.TABLE_NAME),
        project,
        fromStatus,
        toStatus,
        request.allowedRole(),
        InterfaceStatusTransitionStatus.ACTIVE,
        LocalDateTime.now(),
        createdBy
    );
  }

  public static InterfaceStatusTransition applyUpdate(
      InterfaceStatusTransition transition,
      InterfaceStatus fromStatus,
      InterfaceStatus toStatus,
      InterfaceStatusTransitionUpdateRequest request) {
    if (request.allowedRole() != null) {
      transition.setAllowedRole(request.allowedRole());
    }
    if (request.status() != null) {
      transition.setStatus(request.status());
    }
    transition.setFromStatus(fromStatus);
    transition.setToStatus(toStatus);
    return transition;
  }
}

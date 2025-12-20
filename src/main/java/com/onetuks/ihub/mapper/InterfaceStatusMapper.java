package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import java.time.LocalDateTime;

public final class InterfaceStatusMapper {

  private InterfaceStatusMapper() {
  }

  public static InterfaceStatusResponse toResponse(InterfaceStatus status) {
    return new InterfaceStatusResponse(
        status.getStatusId(),
        status.getProject() != null ? status.getProject().getProjectId() : null,
        status.getName(),
        status.getCode(),
        status.getSeqOrder(),
        status.getIsDefault(),
        status.getCreatedAt(),
        status.getUpdatedAt());
  }

  public static void applyCreate(InterfaceStatus status, InterfaceStatusCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    status.setName(request.name());
    status.setCode(request.code());
    status.setSeqOrder(request.seqOrder());
    status.setIsDefault(request.isDefault());
    status.setCreatedAt(now);
    status.setUpdatedAt(now);
  }

  public static void applyUpdate(InterfaceStatus status, InterfaceStatusUpdateRequest request) {
    if (request.name() != null) {
      status.setName(request.name());
    }
    if (request.code() != null) {
      status.setCode(request.code());
    }
    if (request.seqOrder() != null) {
      status.setSeqOrder(request.seqOrder());
    }
    if (request.isDefault() != null) {
      status.setIsDefault(request.isDefault());
    }
    status.setUpdatedAt(LocalDateTime.now());
  }
}

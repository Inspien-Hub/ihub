package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.task.TaskFilterGroupStatusCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusResponse;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusUpdateRequest;
import com.onetuks.ihub.entity.task.TaskFilterGroupStatus;
import java.time.LocalDateTime;

public final class TaskFilterGroupStatusMapper {

  private TaskFilterGroupStatusMapper() {
  }

  public static TaskFilterGroupStatusResponse toResponse(TaskFilterGroupStatus status) {
    return new TaskFilterGroupStatusResponse(
        status.getStatusId(),
        status.getGroup() != null ? status.getGroup().getGroupId() : null,
        status.getStatusType(),
        status.getCreatedAt());
  }

  public static void applyCreate(TaskFilterGroupStatus status,
      TaskFilterGroupStatusCreateRequest request) {
    status.setStatusType(request.statusType());
    status.setCreatedAt(LocalDateTime.now());
  }

  public static void applyUpdate(TaskFilterGroupStatus status,
      TaskFilterGroupStatusUpdateRequest request) {
    if (request.statusType() != null) {
      status.setStatusType(request.statusType());
    }
  }
}

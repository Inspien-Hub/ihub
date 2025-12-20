package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupStatusType;
import java.time.LocalDateTime;

public record TaskFilterGroupStatusResponse(
    Long statusId,
    Long groupId,
    TaskFilterGroupStatusType statusType,
    LocalDateTime createdAt
) {
}

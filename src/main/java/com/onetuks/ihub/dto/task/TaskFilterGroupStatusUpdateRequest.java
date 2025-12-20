package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupStatusType;

public record TaskFilterGroupStatusUpdateRequest(
    TaskFilterGroupStatusType statusType
) {
}

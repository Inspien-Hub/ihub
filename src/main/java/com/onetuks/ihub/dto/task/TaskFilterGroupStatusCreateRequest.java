package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupStatusType;
import jakarta.validation.constraints.NotNull;

public record TaskFilterGroupStatusCreateRequest(
    @NotNull Long groupId,
    @NotNull TaskFilterGroupStatusType statusType
) {
}

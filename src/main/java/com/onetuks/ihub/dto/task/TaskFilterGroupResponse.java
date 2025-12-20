package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupDateType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskFilterGroupResponse(
    Long groupId,
    Long userId,
    Long projectId,
    String name,
    String assigneeKeyword,
    String authorKeyword,
    TaskFilterGroupDateType dateType,
    LocalDate dateFrom,
    LocalDate dateTo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {
}

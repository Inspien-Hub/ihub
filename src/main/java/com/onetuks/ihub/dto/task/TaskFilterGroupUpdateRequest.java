package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupDateType;
import java.time.LocalDate;

public record TaskFilterGroupUpdateRequest(
    String name,
    String assigneeKeyword,
    String authorKeyword,
    TaskFilterGroupDateType dateType,
    LocalDate dateFrom,
    LocalDate dateTo,
    LocalDate deletedAt
) {
}

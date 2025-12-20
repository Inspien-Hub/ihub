package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TaskCreateRequest(
    @NotNull Long projectId,
    Long parentTaskId,
    @NotNull TaskType taskType,
    Long interfaceId,
    @NotBlank String title,
    String description,
    @NotNull TaskStatus status,
    Long assigneeId,
    Long requesterId,
    LocalDate startDate,
    LocalDate dueDate,
    TaskPriority priority,
    Integer progress,
    Long createdById
) {
}

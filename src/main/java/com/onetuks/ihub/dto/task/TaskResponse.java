package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
    Long taskId,
    Long projectId,
    Long parentTaskId,
    TaskType taskType,
    Long interfaceId,
    String title,
    String description,
    TaskStatus status,
    Long assigneeId,
    Long requesterId,
    LocalDate startDate,
    LocalDate dueDate,
    TaskPriority priority,
    Integer progress,
    Long createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

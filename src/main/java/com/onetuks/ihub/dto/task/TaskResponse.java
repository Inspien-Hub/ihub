package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
    String taskId,
    String projectId,
    String parentTaskId,
    TaskType taskType,
    String interfaceId,
    String title,
    String description,
    TaskStatus status,
    String assigneeId,
    String requesterId,
    LocalDate startDate,
    LocalDate dueDate,
    TaskPriority priority,
    Integer progress,
    String createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

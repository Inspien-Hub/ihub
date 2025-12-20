package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import java.time.LocalDate;

public record TaskUpdateRequest(
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
    Integer progress
) {
}

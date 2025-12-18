package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectResponse(
    Long projectId,
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    ProjectStatus status,
    Long createdById,
    Long currentAdminId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

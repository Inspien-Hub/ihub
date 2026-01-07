package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectResponse(
    String projectId,
    String projectName,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    ProjectStatus projectStatus,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    String currentAdminId,
    UserStatus currentAdminStatus,
    String currentAdminName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    boolean isFavorite
) {

}

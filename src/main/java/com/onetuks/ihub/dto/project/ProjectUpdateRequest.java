package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectStatus;
import java.time.LocalDate;

public record ProjectUpdateRequest(
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    ProjectStatus status
) {

}

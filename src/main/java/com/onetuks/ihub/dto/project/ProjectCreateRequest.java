package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ProjectCreateRequest(
    @NotBlank String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    @NotNull ProjectStatus status
) {

}

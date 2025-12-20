package com.onetuks.ihub.dto.event;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EventCreateRequest(
    @NotNull Long projectId,
    String title,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String location,
    String content,
    Integer remindBeforeMinutes,
    Long createdById
) {
}

package com.onetuks.ihub.dto.event;

import java.time.LocalDateTime;

public record EventResponse(
    Long eventId,
    Long projectId,
    String title,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String location,
    String content,
    Integer remindBeforeMinutes,
    Long createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

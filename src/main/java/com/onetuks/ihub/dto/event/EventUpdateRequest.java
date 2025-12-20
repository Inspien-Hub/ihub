package com.onetuks.ihub.dto.event;

import java.time.LocalDateTime;

public record EventUpdateRequest(
    String title,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String location,
    String content,
    Integer remindBeforeMinutes
) {
}

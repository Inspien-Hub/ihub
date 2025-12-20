package com.onetuks.ihub.dto.event;

import com.onetuks.ihub.entity.event.EventAttendeeStatus;

public record EventAttendeeResponse(
    Long eventAttendeeId,
    Long eventId,
    Long userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {
}

package com.onetuks.ihub.dto.event;

import com.onetuks.ihub.entity.event.EventAttendeeStatus;

public record EventAttendeeResponse(
    String eventAttendeeId,
    String eventId,
    String userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {
}

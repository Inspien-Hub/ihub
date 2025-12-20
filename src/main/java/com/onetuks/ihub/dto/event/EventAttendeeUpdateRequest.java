package com.onetuks.ihub.dto.event;

import com.onetuks.ihub.entity.event.EventAttendeeStatus;

public record EventAttendeeUpdateRequest(
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {
}

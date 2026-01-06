package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.EventAttendeeStatus;

public record EventAttendeeUpdateRequest(
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

}

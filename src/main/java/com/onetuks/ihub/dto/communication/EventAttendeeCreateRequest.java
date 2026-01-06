package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.EventAttendeeStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record EventAttendeeCreateRequest(
    @NotNull String eventId,
    @NotNull String userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

  public record EventAttendeeCreateRequests(
      List<EventAttendeeCreateRequest> requests
  ) {

  }

}

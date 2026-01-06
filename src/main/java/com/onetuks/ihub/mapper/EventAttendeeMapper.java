package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeResponse;
import com.onetuks.ihub.dto.communication.EventAttendeeUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.user.User;
import java.util.Objects;

public final class EventAttendeeMapper {

  private EventAttendeeMapper() {
  }

  public static EventAttendeeResponse toResponse(EventAttendee eventAttendee) {
    return new EventAttendeeResponse(
        eventAttendee.getEventAttendeeId(),
        eventAttendee.getEvent() != null ? eventAttendee.getEvent().getEventId() : null,
        eventAttendee.getUser() != null ? eventAttendee.getUser().getUserId() : null,
        eventAttendee.getIsMandatory(),
        eventAttendee.getAttendStatus());
  }

  public static EventAttendee applyCreate(Event event, User user,
      EventAttendeeCreateRequest request) {
    return new EventAttendee(
        UUIDProvider.provideUUID(EventAttendee.TABLE_NAME),
        event,
        user,
        request.isMandatory(),
        request.attendStatus()
    );
  }

  public static EventAttendee applyUpdate(
      EventAttendee eventAttendee, EventAttendeeUpdateRequest request) {
    if (!Objects.isNull(request.isMandatory())) {
      eventAttendee.setIsMandatory(request.isMandatory());
    }
    if (!Objects.isNull(request.attendStatus())) {
      eventAttendee.setAttendStatus(request.attendStatus());
    }
    return eventAttendee;
  }
}

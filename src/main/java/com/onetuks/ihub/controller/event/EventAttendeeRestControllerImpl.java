package com.onetuks.ihub.controller.event;

import com.onetuks.ihub.dto.event.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.event.EventAttendeeResponse;
import com.onetuks.ihub.dto.event.EventAttendeeUpdateRequest;
import com.onetuks.ihub.service.event.EventAttendeeService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventAttendeeRestControllerImpl implements EventAttendeeRestController {

  private final EventAttendeeService eventAttendeeService;

  @Override
  public ResponseEntity<EventAttendeeResponse> createEventAttendee(
      @Valid @RequestBody EventAttendeeCreateRequest request) {
    EventAttendeeResponse response = eventAttendeeService.create(request);
    return ResponseEntity.created(URI.create("/api/event-attendees/" + response.eventAttendeeId()))
        .body(response);
  }

  @Override
  public ResponseEntity<EventAttendeeResponse> getEventAttendee(Long eventAttendeeId) {
    return ResponseEntity.ok(eventAttendeeService.getById(eventAttendeeId));
  }

  @Override
  public ResponseEntity<List<EventAttendeeResponse>> getEventAttendees() {
    return ResponseEntity.ok(eventAttendeeService.getAll());
  }

  @Override
  public ResponseEntity<EventAttendeeResponse> updateEventAttendee(
      Long eventAttendeeId, @Valid @RequestBody EventAttendeeUpdateRequest request) {
    return ResponseEntity.ok(eventAttendeeService.update(eventAttendeeId, request));
  }

  @Override
  public ResponseEntity<Void> deleteEventAttendee(Long eventAttendeeId) {
    eventAttendeeService.delete(eventAttendeeId);
    return ResponseEntity.noContent().build();
  }
}

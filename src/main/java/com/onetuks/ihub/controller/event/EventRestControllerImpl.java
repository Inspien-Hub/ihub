package com.onetuks.ihub.controller.event;

import com.onetuks.ihub.dto.event.EventCreateRequest;
import com.onetuks.ihub.dto.event.EventResponse;
import com.onetuks.ihub.dto.event.EventUpdateRequest;
import com.onetuks.ihub.mapper.EventMapper;
import com.onetuks.ihub.service.event.EventService;
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
public class EventRestControllerImpl implements EventRestController {

  private final EventService eventService;

  @Override
  public ResponseEntity<EventResponse> createEvent(
      @Valid @RequestBody EventCreateRequest request) {
    EventResponse response = EventMapper.toResponse(eventService.create(request));
    return ResponseEntity.created(URI.create("/api/events/" + response.eventId()))
        .body(response);
  }

  @Override
  public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId) {
    return ResponseEntity.ok(EventMapper.toResponse(eventService.getById(eventId)));
  }

  @Override
  public ResponseEntity<List<EventResponse>> getEvents() {
    return ResponseEntity.ok(eventService.getAll().stream().map(EventMapper::toResponse).toList());
  }

  @Override
  public ResponseEntity<EventResponse> updateEvent(
      @PathVariable Long eventId, @Valid @RequestBody EventUpdateRequest request) {
    return ResponseEntity.ok(EventMapper.toResponse(eventService.update(eventId, request)));
  }

  @Override
  public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
    eventService.delete(eventId);
    return ResponseEntity.noContent().build();
  }
}

package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest.EventAttendeeCreateRequests;
import com.onetuks.ihub.dto.communication.EventAttendeeResponse;
import com.onetuks.ihub.dto.communication.EventAttendeeUpdateRequest;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.mapper.EventAttendeeMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.EventAttendeeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventAttendeeRestControllerImpl implements EventAttendeeRestController {

  private final CurrentUserProvider currentUserProvider;
  private final EventAttendeeService eventAttendeeService;

  @Override
  public ResponseEntity<List<EventAttendeeResponse>> manageEventAttendees(
      @PathVariable String eventId, @Valid @RequestBody EventAttendeeCreateRequests request) {
    List<EventAttendee> results = eventAttendeeService.manageAttendees(
        currentUserProvider.resolveUser(), eventId, request);
    List<EventAttendeeResponse> responses = results.stream()
        .map(EventAttendeeMapper::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<EventAttendeeResponse> addEventAttendee(
      @PathVariable String eventId, @Valid @RequestBody EventAttendeeCreateRequest request) {
    EventAttendee result = eventAttendeeService.addAttendee(currentUserProvider.resolveUser(),
        eventId, request);
    EventAttendeeResponse response = EventAttendeeMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<EventAttendeeResponse> updateEventAttendee(
      @PathVariable String eventAttendeeId,
      @Valid @RequestBody EventAttendeeUpdateRequest request) {
    EventAttendee result =
        eventAttendeeService.updateAttendee(
            currentUserProvider.resolveUser(), eventAttendeeId, request);
    EventAttendeeResponse response = EventAttendeeMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Page<EventAttendeeResponse>> getEventAttendees(
      @PathVariable String eventId, @PageableDefault Pageable pageable) {
    Page<EventAttendee> results = eventAttendeeService.getAllAttendees(
        currentUserProvider.resolveUser(), eventId, pageable);
    return ResponseEntity.ok(results.map(EventAttendeeMapper::toResponse));
  }
}

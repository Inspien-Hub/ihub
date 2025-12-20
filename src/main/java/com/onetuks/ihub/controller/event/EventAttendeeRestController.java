package com.onetuks.ihub.controller.event;

import com.onetuks.ihub.dto.event.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.event.EventAttendeeResponse;
import com.onetuks.ihub.dto.event.EventAttendeeUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/event-attendees")
@Tag(name = "EventAttendee", description = "Event attendee management APIs")
public interface EventAttendeeRestController {

  @Operation(summary = "Create event attendee")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Event attendee created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<EventAttendeeResponse> createEventAttendee(
      @Valid @RequestBody EventAttendeeCreateRequest request);

  @Operation(summary = "Get event attendee by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Event attendee found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Event attendee not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{eventAttendeeId}")
  ResponseEntity<EventAttendeeResponse> getEventAttendee(@PathVariable Long eventAttendeeId);

  @Operation(summary = "List event attendees")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Event attendees listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<EventAttendeeResponse>> getEventAttendees();

  @Operation(summary = "Update event attendee")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Event attendee updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Event attendee not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{eventAttendeeId}")
  ResponseEntity<EventAttendeeResponse> updateEventAttendee(
      @PathVariable Long eventAttendeeId,
      @Valid @RequestBody EventAttendeeUpdateRequest request);

  @Operation(summary = "Delete event attendee")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Event attendee deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Event attendee not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{eventAttendeeId}")
  ResponseEntity<Void> deleteEventAttendee(@PathVariable Long eventAttendeeId);
}

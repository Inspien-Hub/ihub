package com.onetuks.ihub.controller.event;

import com.onetuks.ihub.dto.event.EventCreateRequest;
import com.onetuks.ihub.dto.event.EventResponse;
import com.onetuks.ihub.dto.event.EventUpdateRequest;
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

@RequestMapping("/api/events")
@Tag(name = "Event", description = "Event management APIs")
public interface EventRestController {

  @Operation(summary = "Create event")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Event created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventCreateRequest request);

  @Operation(summary = "Get event by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Event found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Event not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{eventId}")
  ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId);

  @Operation(summary = "List events")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Events listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<EventResponse>> getEvents();

  @Operation(summary = "Update event")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Event updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Event not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{eventId}")
  ResponseEntity<EventResponse> updateEvent(
      @PathVariable Long eventId,
      @Valid @RequestBody EventUpdateRequest request);

  @Operation(summary = "Delete event")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Event deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Event not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{eventId}")
  ResponseEntity<Void> deleteEvent(@PathVariable Long eventId);
}

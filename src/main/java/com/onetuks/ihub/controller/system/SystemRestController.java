package com.onetuks.ihub.controller.system;

import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemResponse;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
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

@RequestMapping("/api/systems")
@Tag(name = "System", description = "System management APIs")
public interface SystemRestController {

  @Operation(summary = "Create system")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "System created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<SystemResponse> createSystem(@Valid @RequestBody SystemCreateRequest request);

  @Operation(summary = "Get system by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "System found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "System not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{systemId}")
  ResponseEntity<SystemResponse> getSystem(@PathVariable Long systemId);

  @Operation(summary = "List systems")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Systems listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<SystemResponse>> getSystems();

  @Operation(summary = "Update system")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "System updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "System not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{systemId}")
  ResponseEntity<SystemResponse> updateSystem(
      @PathVariable Long systemId,
      @Valid @RequestBody SystemUpdateRequest request);

  @Operation(summary = "Delete system")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "System deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "System not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{systemId}")
  ResponseEntity<Void> deleteSystem(@PathVariable Long systemId);
}

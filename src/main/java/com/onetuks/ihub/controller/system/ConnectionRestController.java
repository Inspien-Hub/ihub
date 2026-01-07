package com.onetuks.ihub.controller.system;

import com.onetuks.ihub.dto.system.ConnectionCreateRequest;
import com.onetuks.ihub.dto.system.ConnectionResponse;
import com.onetuks.ihub.dto.system.ConnectionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Tag(name = "Connection", description = "Connection management APIs")
public interface ConnectionRestController {

  @Operation(summary = "List connections")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Connections listed"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/systems/{systemId}/connections")
  ResponseEntity<Page<ConnectionResponse>> getConnections(
      @PathVariable String systemId,
      @PageableDefault Pageable pageable);

  @Operation(summary = "Create connection")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Connection created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/api/systems/{systemId}/connections")
  ResponseEntity<ConnectionResponse> createConnection(
      @PathVariable String systemId,
      @Valid @RequestBody ConnectionCreateRequest request);

  @Operation(summary = "Get connection by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Connection found"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "404", description = "Connection not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/connections/{connectionId}")
  ResponseEntity<ConnectionResponse> getConnection(@PathVariable String connectionId);


  @Operation(summary = "Update connection")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Connection updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "404", description = "Connection not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/api/connections/{connectionId}")
  ResponseEntity<ConnectionResponse> updateConnection(
      @PathVariable String connectionId,
      @Valid @RequestBody ConnectionUpdateRequest request);

  @Operation(summary = "Delete connection")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Connection deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "404", description = "Connection not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/api/connetions/{connectionId}")
  ResponseEntity<Void> deleteConnection(@PathVariable String connectionId);
}

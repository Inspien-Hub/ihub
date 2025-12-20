package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
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

@RequestMapping("/api/interface-statuses")
@Tag(name = "InterfaceStatus", description = "Interface status management APIs")
public interface InterfaceStatusRestController {

  @Operation(summary = "Create interface status")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Interface status created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<InterfaceStatusResponse> createInterfaceStatus(
      @Valid @RequestBody InterfaceStatusCreateRequest request);

  @Operation(summary = "Get interface status by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface status found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface status not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{statusId}")
  ResponseEntity<InterfaceStatusResponse> getInterfaceStatus(@PathVariable Long statusId);

  @Operation(summary = "List interface statuses")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface statuses listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<InterfaceStatusResponse>> getInterfaceStatuses();

  @Operation(summary = "Update interface status")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface status updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Interface status not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{statusId}")
  ResponseEntity<InterfaceStatusResponse> updateInterfaceStatus(
      @PathVariable Long statusId,
      @Valid @RequestBody InterfaceStatusUpdateRequest request);

  @Operation(summary = "Delete interface status")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Interface status deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface status not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{statusId}")
  ResponseEntity<Void> deleteInterfaceStatus(@PathVariable Long statusId);
}

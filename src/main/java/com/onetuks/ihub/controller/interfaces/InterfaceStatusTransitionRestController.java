package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/interface-status-transitions")
@Tag(name = "InterfaceStatusTransition", description = "Interface status transition management APIs")
public interface InterfaceStatusTransitionRestController {

  @Operation(summary = "Create interface status transition")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Interface status transition created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<InterfaceStatusTransitionResponse> createInterfaceStatusTransition(
      @Valid @RequestBody InterfaceStatusTransitionCreateRequest request);

  @Operation(summary = "List interface status transitions")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Interface status transitions listed"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<Page<InterfaceStatusTransitionResponse>> getInterfaceStatusTransitions(
      @PageableDefault Pageable pageable);

  @Operation(summary = "Update interface status transition")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Interface status transition updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "404", description = "Interface status transition not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{transitionId}")
  ResponseEntity<InterfaceStatusTransitionResponse> updateInterfaceStatusTransition(
      @PathVariable String transitionId,
      @Valid @RequestBody InterfaceStatusTransitionUpdateRequest request);
}

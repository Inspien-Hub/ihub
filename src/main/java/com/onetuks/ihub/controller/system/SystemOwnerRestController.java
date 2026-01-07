package com.onetuks.ihub.controller.system;

import com.onetuks.ihub.dto.system.SystemOwnerCreateRequest;
import com.onetuks.ihub.dto.system.SystemOwnerResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
@Tag(name = "System Owner", description = "System owner management APIs")
public interface SystemOwnerRestController {

  @Operation(summary = "List system owners")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "System owners listed"),
      @ApiResponse(responseCode = "401", description = "Authentication required"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/projects/{projectId}/system-owners")
  ResponseEntity<Page<SystemOwnerResponse>> getSystemOwners(
      @PathVariable String projectId,
      @RequestParam(required = false) String userId,
      @PageableDefault Pageable pageable);

  @Operation(summary = "Create system owner")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "System owner created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "Authentication required"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/api/projects/{projectId}/system-owners")
  ResponseEntity<SystemOwnerResponse> createSystemOwner(
      @PathVariable String projectId,
      @Valid @RequestBody SystemOwnerCreateRequest request);

  @Operation(summary = "Delete system owner")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "System owner deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "Authentication required"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "System owner not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/api/system-owners/{systemOwnerId}")
  ResponseEntity<Void> deleteSystemOwner(@PathVariable String systemOwnerId);
}

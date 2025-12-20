package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceUpdateRequest;
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

@RequestMapping("/api/interfaces")
@Tag(name = "Interface", description = "Interface management APIs")
public interface InterfaceRestController {

  @Operation(summary = "Create interface")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Interface created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<InterfaceResponse> createInterface(
      @Valid @RequestBody InterfaceCreateRequest request);

  @Operation(summary = "Get interface by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{interfaceId}")
  ResponseEntity<InterfaceResponse> getInterface(@PathVariable Long interfaceId);

  @Operation(summary = "List interfaces")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interfaces listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<InterfaceResponse>> getInterfaces();

  @Operation(summary = "Update interface")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Interface not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{interfaceId}")
  ResponseEntity<InterfaceResponse> updateInterface(
      @PathVariable Long interfaceId,
      @Valid @RequestBody InterfaceUpdateRequest request);

  @Operation(summary = "Delete interface")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Interface deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{interfaceId}")
  ResponseEntity<Void> deleteInterface(@PathVariable Long interfaceId);
}

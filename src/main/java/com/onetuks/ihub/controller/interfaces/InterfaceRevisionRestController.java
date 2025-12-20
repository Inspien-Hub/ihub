package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceRevisionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceRevisionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceRevisionUpdateRequest;
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

@RequestMapping("/api/interface-revisions")
@Tag(name = "InterfaceRevision", description = "Interface revision management APIs")
public interface InterfaceRevisionRestController {

  @Operation(summary = "Create interface revision")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Interface revision created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<InterfaceRevisionResponse> createInterfaceRevision(
      @Valid @RequestBody InterfaceRevisionCreateRequest request);

  @Operation(summary = "Get interface revision by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface revision found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface revision not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{revisionId}")
  ResponseEntity<InterfaceRevisionResponse> getInterfaceRevision(@PathVariable Long revisionId);

  @Operation(summary = "List interface revisions")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface revisions listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<InterfaceRevisionResponse>> getInterfaceRevisions();

  @Operation(summary = "Update interface revision")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface revision updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Interface revision not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{revisionId}")
  ResponseEntity<InterfaceRevisionResponse> updateInterfaceRevision(
      @PathVariable Long revisionId,
      @Valid @RequestBody InterfaceRevisionUpdateRequest request);

  @Operation(summary = "Delete interface revision")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Interface revision deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface revision not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{revisionId}")
  ResponseEntity<Void> deleteInterfaceRevision(@PathVariable Long revisionId);
}

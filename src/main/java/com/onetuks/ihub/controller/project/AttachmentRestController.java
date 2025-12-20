package com.onetuks.ihub.controller.project;

import com.onetuks.ihub.dto.project.AttachmentCreateRequest;
import com.onetuks.ihub.dto.project.AttachmentResponse;
import com.onetuks.ihub.dto.project.AttachmentUpdateRequest;
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

@RequestMapping("/api/attachments")
@Tag(name = "Attachment", description = "Attachment management APIs")
public interface AttachmentRestController {

  @Operation(summary = "Create attachment")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Attachment created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<AttachmentResponse> createAttachment(
      @Valid @RequestBody AttachmentCreateRequest request);

  @Operation(summary = "Get attachment by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Attachment found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Attachment not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{attachmentId}")
  ResponseEntity<AttachmentResponse> getAttachment(@PathVariable Long attachmentId);

  @Operation(summary = "List attachments")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Attachments listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<AttachmentResponse>> getAttachments();

  @Operation(summary = "Update attachment")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Attachment updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Attachment not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{attachmentId}")
  ResponseEntity<AttachmentResponse> updateAttachment(
      @PathVariable Long attachmentId,
      @Valid @RequestBody AttachmentUpdateRequest request);

  @Operation(summary = "Delete attachment")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Attachment deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Attachment not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{attachmentId}")
  ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId);
}

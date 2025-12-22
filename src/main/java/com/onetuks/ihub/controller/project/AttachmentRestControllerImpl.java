package com.onetuks.ihub.controller.project;

import com.onetuks.ihub.dto.project.AttachmentCreateRequest;
import com.onetuks.ihub.dto.project.AttachmentResponse;
import com.onetuks.ihub.dto.project.AttachmentUpdateRequest;
import com.onetuks.ihub.mapper.AttachmentMapper;
import com.onetuks.ihub.service.project.AttachmentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AttachmentRestControllerImpl implements AttachmentRestController {

  private final AttachmentService attachmentService;

  @Override
  public ResponseEntity<AttachmentResponse> createAttachment(
      @Valid @RequestBody AttachmentCreateRequest request) {
    AttachmentResponse response = AttachmentMapper.toResponse(attachmentService.create(request));
    return ResponseEntity.created(URI.create("/api/attachments/" + response.attachmentId()))
        .body(response);
  }

  @Override
  public ResponseEntity<AttachmentResponse> getAttachment(@PathVariable Long attachmentId) {
    return ResponseEntity.ok(AttachmentMapper.toResponse(attachmentService.getById(attachmentId)));
  }

  @Override
  public ResponseEntity<List<AttachmentResponse>> getAttachments() {
    return ResponseEntity.ok(
        attachmentService.getAll().stream().map(AttachmentMapper::toResponse).toList());
  }

  @Override
  public ResponseEntity<AttachmentResponse> updateAttachment(
      @PathVariable Long attachmentId, @Valid @RequestBody AttachmentUpdateRequest request) {
    return ResponseEntity.ok(
        AttachmentMapper.toResponse(attachmentService.update(attachmentId, request)));
  }

  @Override
  public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId) {
    attachmentService.delete(attachmentId);
    return ResponseEntity.noContent().build();
  }
}

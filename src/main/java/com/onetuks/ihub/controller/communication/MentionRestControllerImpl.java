package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.MentionCreateRequest;
import com.onetuks.ihub.dto.communication.MentionResponse;
import com.onetuks.ihub.dto.communication.MentionUpdateRequest;
import com.onetuks.ihub.service.communication.MentionService;
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
public class MentionRestControllerImpl implements MentionRestController {

  private final MentionService mentionService;

  @Override
  public ResponseEntity<MentionResponse> createMention(
      @Valid @RequestBody MentionCreateRequest request) {
    MentionResponse response = mentionService.create(request);
    return ResponseEntity.created(URI.create("/api/mentions/" + response.mentionId()))
        .body(response);
  }

  @Override
  public ResponseEntity<MentionResponse> getMention(@PathVariable Long mentionId) {
    return ResponseEntity.ok(mentionService.getById(mentionId));
  }

  @Override
  public ResponseEntity<List<MentionResponse>> getMentions() {
    return ResponseEntity.ok(mentionService.getAll());
  }

  @Override
  public ResponseEntity<MentionResponse> updateMention(
      @PathVariable Long mentionId, @Valid @RequestBody MentionUpdateRequest request) {
    return ResponseEntity.ok(mentionService.update(mentionId, request));
  }

  @Override
  public ResponseEntity<Void> deleteMention(@PathVariable Long mentionId) {
    mentionService.delete(mentionId);
    return ResponseEntity.noContent().build();
  }
}

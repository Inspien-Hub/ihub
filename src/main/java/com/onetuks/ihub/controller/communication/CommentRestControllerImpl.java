package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentResponse;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.service.communication.CommentService;
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
public class CommentRestControllerImpl implements CommentRestController {

  private final CommentService commentService;

  @Override
  public ResponseEntity<CommentResponse> createComment(
      @Valid @RequestBody CommentCreateRequest request) {
    CommentResponse response = commentService.create(request);
    return ResponseEntity.created(URI.create("/api/comments/" + response.commentId()))
        .body(response);
  }

  @Override
  public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
    return ResponseEntity.ok(commentService.getById(commentId));
  }

  @Override
  public ResponseEntity<List<CommentResponse>> getComments() {
    return ResponseEntity.ok(commentService.getAll());
  }

  @Override
  public ResponseEntity<CommentResponse> updateComment(
      @PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest request) {
    return ResponseEntity.ok(commentService.update(commentId, request));
  }

  @Override
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    commentService.delete(commentId);
    return ResponseEntity.noContent().build();
  }
}

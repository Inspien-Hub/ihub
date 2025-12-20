package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostResponse;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import com.onetuks.ihub.service.communication.PostService;
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
public class PostRestControllerImpl implements PostRestController {

  private final PostService postService;

  @Override
  public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
    PostResponse response = postService.create(request);
    return ResponseEntity.created(URI.create("/api/posts/" + response.postId()))
        .body(response);
  }

  @Override
  public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
    return ResponseEntity.ok(postService.getById(postId));
  }

  @Override
  public ResponseEntity<List<PostResponse>> getPosts() {
    return ResponseEntity.ok(postService.getAll());
  }

  @Override
  public ResponseEntity<PostResponse> updatePost(
      @PathVariable Long postId, @Valid @RequestBody PostUpdateRequest request) {
    return ResponseEntity.ok(postService.update(postId, request));
  }

  @Override
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postService.delete(postId);
    return ResponseEntity.noContent().build();
  }
}

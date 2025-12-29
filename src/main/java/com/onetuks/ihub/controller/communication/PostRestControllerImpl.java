package com.onetuks.ihub.controller.communication;

import static com.onetuks.ihub.config.RoleDataInitializer.POST_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostResponse;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.mapper.PostMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.PostService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostRestControllerImpl implements PostRestController {

  private final CurrentUserProvider currentUserProvider;
  private final PostService postService;

  @RequiresRole({POST_FULL_ACCESS})
  @Override
  public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
    Post result = postService.create(currentUserProvider.resolveUser(), request);
    PostResponse response = PostMapper.toResponse(result);
    return ResponseEntity.created(URI.create("/api/posts/" + response.postId()))
        .body(response);
  }

  @RequiresRole({POST_FULL_ACCESS})
  @Override
  public ResponseEntity<PostResponse> getPost(@PathVariable String postId) {
    Post result = postService.getById(currentUserProvider.resolveUser(), postId);
    return ResponseEntity.ok(PostMapper.toResponse(result));
  }

  @RequiresRole({POST_FULL_ACCESS})
  @Override
  public ResponseEntity<Page<PostResponse>> getPosts(
      @RequestParam String projectId, @PageableDefault Pageable pageable) {
    Page<Post> results = postService.getAll(
        currentUserProvider.resolveUser(), projectId,
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    return ResponseEntity.ok(results.map(PostMapper::toResponse));
  }

  @RequiresRole({POST_FULL_ACCESS})
  @Override
  public ResponseEntity<PostResponse> updatePost(
      @PathVariable String postId, @Valid @RequestBody PostUpdateRequest request) {
    Post result = postService.update(currentUserProvider.resolveUser(), postId, request);
    return ResponseEntity.ok(PostMapper.toResponse(result));
  }

  @RequiresRole({POST_FULL_ACCESS})
  @Override
  public ResponseEntity<Void> deletePost(@PathVariable String postId) {
    postService.delete(currentUserProvider.resolveUser(), postId);
    return ResponseEntity.noContent().build();
  }
}

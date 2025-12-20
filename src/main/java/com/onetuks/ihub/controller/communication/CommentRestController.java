package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentResponse;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
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

@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "Comment management APIs")
public interface CommentRestController {

  @Operation(summary = "Create comment")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Comment created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<CommentResponse> createComment(
      @Valid @RequestBody CommentCreateRequest request);

  @Operation(summary = "Get comment by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Comment found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Comment not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{commentId}")
  ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId);

  @Operation(summary = "List comments")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Comments listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<CommentResponse>> getComments();

  @Operation(summary = "Update comment")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Comment updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Comment not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{commentId}")
  ResponseEntity<CommentResponse> updateComment(
      @PathVariable Long commentId,
      @Valid @RequestBody CommentUpdateRequest request);

  @Operation(summary = "Delete comment")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Comment deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Comment not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{commentId}")
  ResponseEntity<Void> deleteComment(@PathVariable Long commentId);
}

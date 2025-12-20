package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.FeedItemCreateRequest;
import com.onetuks.ihub.dto.communication.FeedItemResponse;
import com.onetuks.ihub.dto.communication.FeedItemUpdateRequest;
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

@RequestMapping("/api/feed-items")
@Tag(name = "FeedItem", description = "Feed item management APIs")
public interface FeedItemRestController {

  @Operation(summary = "Create feed item")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Feed item created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<FeedItemResponse> createFeedItem(
      @Valid @RequestBody FeedItemCreateRequest request);

  @Operation(summary = "Get feed item by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Feed item found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Feed item not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{feedItemId}")
  ResponseEntity<FeedItemResponse> getFeedItem(@PathVariable Long feedItemId);

  @Operation(summary = "List feed items")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Feed items listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<FeedItemResponse>> getFeedItems();

  @Operation(summary = "Update feed item")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Feed item updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Feed item not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{feedItemId}")
  ResponseEntity<FeedItemResponse> updateFeedItem(
      @PathVariable Long feedItemId,
      @Valid @RequestBody FeedItemUpdateRequest request);

  @Operation(summary = "Delete feed item")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Feed item deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Feed item not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{feedItemId}")
  ResponseEntity<Void> deleteFeedItem(@PathVariable Long feedItemId);
}

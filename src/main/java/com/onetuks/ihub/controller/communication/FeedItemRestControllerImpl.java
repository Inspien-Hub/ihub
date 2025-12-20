package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.FeedItemCreateRequest;
import com.onetuks.ihub.dto.communication.FeedItemResponse;
import com.onetuks.ihub.dto.communication.FeedItemUpdateRequest;
import com.onetuks.ihub.service.communication.FeedItemService;
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
public class FeedItemRestControllerImpl implements FeedItemRestController {

  private final FeedItemService feedItemService;

  @Override
  public ResponseEntity<FeedItemResponse> createFeedItem(
      @Valid @RequestBody FeedItemCreateRequest request) {
    FeedItemResponse response = feedItemService.create(request);
    return ResponseEntity.created(URI.create("/api/feed-items/" + response.feedId()))
        .body(response);
  }

  @Override
  public ResponseEntity<FeedItemResponse> getFeedItem(@PathVariable Long feedItemId) {
    return ResponseEntity.ok(feedItemService.getById(feedItemId));
  }

  @Override
  public ResponseEntity<List<FeedItemResponse>> getFeedItems() {
    return ResponseEntity.ok(feedItemService.getAll());
  }

  @Override
  public ResponseEntity<FeedItemResponse> updateFeedItem(
      @PathVariable Long feedItemId, @Valid @RequestBody FeedItemUpdateRequest request) {
    return ResponseEntity.ok(feedItemService.update(feedItemId, request));
  }

  @Override
  public ResponseEntity<Void> deleteFeedItem(@PathVariable Long feedItemId) {
    feedItemService.delete(feedItemId);
    return ResponseEntity.noContent().build();
  }
}

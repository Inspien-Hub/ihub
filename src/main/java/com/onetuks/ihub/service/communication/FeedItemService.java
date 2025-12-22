package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.FeedItemCreateRequest;
import com.onetuks.ihub.dto.communication.FeedItemUpdateRequest;
import com.onetuks.ihub.entity.communication.FeedItem;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.FeedItemMapper;
import com.onetuks.ihub.repository.FeedItemJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedItemService {

  private final FeedItemJpaRepository feedItemJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public FeedItem create(FeedItemCreateRequest request) {
    FeedItem feedItem = new FeedItem();
    FeedItemMapper.applyCreate(feedItem, request);
    feedItem.setProject(findProject(request.projectId()));
    if (request.actorId() != null) {
      feedItem.setActor(findUser(request.actorId()));
    }
    return feedItemJpaRepository.save(feedItem);
  }

  @Transactional(readOnly = true)
  public FeedItem getById(Long feedItemId) {
    return findEntity(feedItemId);
  }

  @Transactional(readOnly = true)
  public List<FeedItem> getAll() {
    return feedItemJpaRepository.findAll();
  }

  @Transactional
  public FeedItem update(Long feedItemId, FeedItemUpdateRequest request) {
    FeedItem feedItem = findEntity(feedItemId);
    FeedItemMapper.applyUpdate(feedItem, request);
    if (request.actorId() != null) {
      feedItem.setActor(findUser(request.actorId()));
    }
    return feedItem;
  }

  @Transactional
  public void delete(Long feedItemId) {
    FeedItem feedItem = findEntity(feedItemId);
    feedItemJpaRepository.delete(feedItem);
  }

  private FeedItem findEntity(Long feedItemId) {
    return feedItemJpaRepository.findById(feedItemId)
        .orElseThrow(() -> new EntityNotFoundException("Feed item not found: " + feedItemId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

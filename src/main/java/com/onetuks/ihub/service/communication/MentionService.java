package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.MentionCreateRequest;
import com.onetuks.ihub.dto.communication.MentionUpdateRequest;
import com.onetuks.ihub.entity.communication.Mention;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.MentionMapper;
import com.onetuks.ihub.repository.MentionJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentionService {

  private final MentionJpaRepository mentionJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Mention create(MentionCreateRequest request) {
    Mention mention = new Mention();
    MentionMapper.applyCreate(mention, request);
    mention.setProject(findProject(request.projectId()));
    mention.setMentionedUser(findUser(request.mentionedUserId()));
    mention.setCreatedBy(findUser(request.createdById()));
    return mentionJpaRepository.save(mention);
  }

  @Transactional(readOnly = true)
  public Mention getById(Long mentionId) {
    return findEntity(mentionId);
  }

  @Transactional(readOnly = true)
  public List<Mention> getAll() {
    return mentionJpaRepository.findAll();
  }

  @Transactional
  public Mention update(Long mentionId, MentionUpdateRequest request) {
    Mention mention = findEntity(mentionId);
    MentionMapper.applyUpdate(mention, request);
    if (request.mentionedUserId() != null) {
      mention.setMentionedUser(findUser(request.mentionedUserId()));
    }
    if (request.createdById() != null) {
      mention.setCreatedBy(findUser(request.createdById()));
    }
    return mention;
  }

  @Transactional
  public void delete(Long mentionId) {
    Mention mention = findEntity(mentionId);
    mentionJpaRepository.delete(mention);
  }

  private Mention findEntity(Long mentionId) {
    return mentionJpaRepository.findById(mentionId)
        .orElseThrow(() -> new EntityNotFoundException("Mention not found: " + mentionId));
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

package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.entity.communication.Comment;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.CommentMapper;
import com.onetuks.ihub.repository.CommentJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentJpaRepository commentJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Comment create(CommentCreateRequest request) {
    Comment comment = new Comment();
    CommentMapper.applyCreate(comment, request);
    comment.setProject(findProject(request.projectId()));
    if (request.parentCommentId() != null) {
      comment.setParentComment(findComment(request.parentCommentId()));
    }
    if (request.createdById() != null) {
      comment.setCreatedBy(findUser(request.createdById()));
    }
    return commentJpaRepository.save(comment);
  }

  @Transactional(readOnly = true)
  public Comment getById(Long commentId) {
    return findEntity(commentId);
  }

  @Transactional(readOnly = true)
  public List<Comment> getAll() {
    return commentJpaRepository.findAll();
  }

  @Transactional
  public Comment update(Long commentId, CommentUpdateRequest request) {
    Comment comment = findEntity(commentId);
    CommentMapper.applyUpdate(comment, request);
    return comment;
  }

  @Transactional
  public void delete(Long commentId) {
    Comment comment = findEntity(commentId);
    commentJpaRepository.delete(comment);
  }

  private Comment findEntity(Long commentId) {
    return commentJpaRepository.findById(commentId)
        .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));
  }

  private Comment findComment(Long commentId) {
    return findEntity(commentId);
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

package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.PostMapper;
import com.onetuks.ihub.repository.PostJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostJpaRepository postJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Post create(PostCreateRequest request) {
    Post post = new Post();
    PostMapper.applyCreate(post, request);
    post.setProject(findProject(request.projectId()));
    if (request.createdById() != null) {
      post.setCreatedBy(findUser(request.createdById()));
    }
    return postJpaRepository.save(post);
  }

  @Transactional(readOnly = true)
  public Post getById(Long postId) {
    return findEntity(postId);
  }

  @Transactional(readOnly = true)
  public List<Post> getAll() {
    return postJpaRepository.findAll();
  }

  @Transactional
  public Post update(Long postId, PostUpdateRequest request) {
    Post post = findEntity(postId);
    PostMapper.applyUpdate(post, request);
    return post;
  }

  @Transactional
  public void delete(Long postId) {
    Post post = findEntity(postId);
    postJpaRepository.delete(post);
  }

  private Post findEntity(Long postId) {
    return postJpaRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
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

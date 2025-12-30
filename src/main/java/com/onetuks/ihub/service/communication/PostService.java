package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.entity.communication.PostStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.PostMapper;
import com.onetuks.ihub.repository.PostJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final PostJpaRepository postJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public Post create(User currentUser, PostCreateRequest request) {
    Project project = findProject(request.projectId());
    projectMemberCheckComponent.checkIsProjectMember(currentUser, project.getProjectId());
    return postJpaRepository.save(PostMapper.applyCreate(currentUser, project, request));
  }

  @Transactional(readOnly = true)
  public Post getById(User currentUser, String postId) {
    Post post = findEntity(postId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, post.getProject().getProjectId());
    return post;
  }

  @Transactional(readOnly = true)
  public Page<Post> getAll(User currentUser, String projectId, Pageable pageable) {
    projectMemberCheckComponent.checkIsProjectMember(currentUser, projectId);
    return postJpaRepository.findAllByProject_ProjectId(projectId, pageable);
  }

  @Transactional
  public Post update(User currentUser, String postId, PostUpdateRequest request) {
    Post post = findEntity(postId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, post.getProject().getProjectId());
    PostMapper.applyUpdate(post, request);
    return post;
  }

  @Transactional
  public Post delete(User currentUser, String postId) {
    Post post = findEntity(postId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, post.getProject().getProjectId());
    post.setStatus(PostStatus.DELETED);
    return post;
  }

  private Post findEntity(String postId) {
    return postJpaRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

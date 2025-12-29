package com.onetuks.ihub.service.communication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.entity.communication.PostStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.PostJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class PostServiceTest {

  @Autowired
  private PostService postService;

  @Autowired
  private PostJpaRepository postJpaRepository;
  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  private Project project;
  private User author;

  @BeforeEach
  void setUp() {
    author = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, author, author,
        "PostProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, author);
  }

  @Test
  void createPost_success() {
    PostCreateRequest request = buildCreatePostRequest();

    Post result = postService.create(author, request);

    assertThat(result.getPostId()).isNotNull();
    assertThat(result.getTitle()).isEqualToIgnoringCase(request.title());
    assertThat(result.getCreatedBy().getUserId()).isEqualTo(author.getUserId());
  }

  @Test
  void updatePost_success() {
    Post created = postService.create(author, buildCreatePostRequest());
    PostUpdateRequest updateRequest = new PostUpdateRequest("New", "New content");

    Post result = postService.update(author, created.getPostId(), updateRequest);

    assertThat(result.getTitle()).isEqualToIgnoringCase(updateRequest.title());
    assertThat(result.getContent()).isEqualToIgnoringCase(updateRequest.content());
  }

  @Test
  void getPostById_exception() {
    // Given
    User hacker = ServiceTestDataFactory.createUser(userJpaRepository);
    Post created = postService.create(author, buildCreatePostRequest());

    // When & Then
    assertThatThrownBy(() -> postService.getById(hacker, created.getPostId()))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  void getPosts_returnsAll() {
    long expected = postJpaRepository.count();
    Pageable pageable = PageRequest.of(0, 10);
    postService.create(author, buildCreatePostRequest());
    postService.create(author, buildCreatePostRequest());

    Page<Post> results = postService.getAll(author, project.getProjectId(), pageable);

    assertThat(results.getTotalElements()).isEqualTo(expected);
  }

  @Test
  void deletePost_success() {
    Post created = postService.create(author, buildCreatePostRequest());

    Post result = postService.delete(author, created.getPostId());

    assertThat(result.getStatus()).isEqualTo(PostStatus.DELETED);
  }

  private PostCreateRequest buildCreatePostRequest() {
    return new PostCreateRequest(
        project.getProjectId(),
        "Title",
        "Content");
  }
}

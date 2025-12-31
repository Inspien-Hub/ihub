package com.onetuks.ihub.service.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.entity.communication.Comment;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.PostMapper;
import com.onetuks.ihub.repository.CommentJpaRepository;
import com.onetuks.ihub.repository.MentionJpaRepository;
import com.onetuks.ihub.repository.PostJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class CommentServiceTest {

  @Autowired
  private CommentService commentService;

  @Autowired
  private CommentJpaRepository commentJpaRepository;

  @Autowired
  private MentionJpaRepository mentionJpaRepository;

  @Autowired
  private PostJpaRepository postJpaRepository;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;

  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  private Post post;
  private User author;
  private User mentionedUser;

  @BeforeEach
  void setUp() {
    author = ServiceTestDataFactory.createUser(userJpaRepository);
    mentionedUser = ServiceTestDataFactory.createUser(userJpaRepository);
    Project project =
        ServiceTestDataFactory.createProject(projectJpaRepository, author, author, "CommentProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, author);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, mentionedUser);
    post = postJpaRepository.save(
        PostMapper.applyCreate(author, project,
            new PostCreateRequest(project.getProjectId(), "title", "content"))
    );
  }

  @AfterEach
  void tearDown() {
    mentionJpaRepository.deleteAll();
    commentJpaRepository.deleteAll();
    postJpaRepository.deleteAll();
    projectMemberJpaRepository.deleteAll();
    projectJpaRepository.deleteAll();
    userJpaRepository.deleteAll();
  }

  @Test
  void createComment_success() {
    CommentCreateRequest request = new CommentCreateRequest(
        "Hello",
        null,
        List.of(mentionedUser.getUserId())
    );

    CommentService.CommentCreateResult result =
        commentService.create(author, TargetType.POST, post.getPostId(), request);

    assertNotNull(result.comment().getCommentId());
    assertEquals("Hello", result.comment().getContent());
    assertEquals(TargetType.POST, result.comment().getTargetType());
    assertEquals(1, result.mentions().size());
    assertEquals(mentionedUser.getUserId(),
        result.mentions().getFirst().getMentionedUser().getUserId());
  }

  @Test
  void updateComment_success() {
    CommentService.CommentCreateResult created = commentService.create(
        author,
        TargetType.POST,
        post.getPostId(),
        new CommentCreateRequest("Old", null, null)
    );
    CommentUpdateRequest updateRequest = new CommentUpdateRequest("Updated");

    Comment result = commentService.update(author, created.comment().getCommentId(), updateRequest);

    assertEquals("Updated", result.getContent());
  }

  @Test
  void getComments_returnsAll() {
    commentService.create(author, TargetType.POST, post.getPostId(),
        new CommentCreateRequest("A", null, null));
    commentService.create(author, TargetType.POST, post.getPostId(),
        new CommentCreateRequest("B", null, null));

    List<Comment> results = commentService.getAll(author, TargetType.POST, post.getPostId());

    assertEquals(2, results.size());
  }

  @Test
  void deleteComment_success() {
    CommentService.CommentCreateResult created = commentService.create(
        author,
        TargetType.POST,
        post.getPostId(),
        new CommentCreateRequest("C", null, null)
    );

    commentService.delete(author, created.comment().getCommentId());

    assertEquals(0, commentJpaRepository.count());
    assertThrows(EntityNotFoundException.class,
        () -> commentService.getById(author, created.comment().getCommentId()));
  }

  @Test
  void getComments_deniedForNonMember() {
    User outsider = ServiceTestDataFactory.createUser(userJpaRepository);

    assertThrows(AccessDeniedException.class,
        () -> commentService.getAll(outsider, TargetType.POST, post.getPostId()));
  }
}

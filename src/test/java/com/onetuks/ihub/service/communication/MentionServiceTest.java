package com.onetuks.ihub.service.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.MentionCreateRequest;
import com.onetuks.ihub.entity.communication.Mention;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.MentionMapper;
import com.onetuks.ihub.repository.MentionJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
class MentionServiceTest {

  @Autowired
  private MentionService mentionService;

  @Autowired
  private MentionJpaRepository mentionJpaRepository;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;

  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  private Project project;
  private User mentioned;
  private User author;

  @BeforeEach
  void setUp() {
    author = ServiceTestDataFactory.createUser(userJpaRepository);
    mentioned = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, author, author, "MentionProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, author);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, mentioned);
  }

  @AfterEach
  void tearDown() {
    mentionJpaRepository.deleteAll();
    projectMemberJpaRepository.deleteAll();
    projectJpaRepository.deleteAll();
    userJpaRepository.deleteAll();
  }

  @Test
  void getMyMentions_filtersByProjectAndAuthor() {
    Project otherProject = ServiceTestDataFactory.createProject(projectJpaRepository, author, author, "Other");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, otherProject, author);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, otherProject, mentioned);

    saveMention(project, TargetType.POST, "1", mentioned, author, LocalDateTime.now().minusDays(1));
    saveMention(otherProject, TargetType.TASK, "2", mentioned, author, LocalDateTime.now());

    Pageable pageable = PageRequest.of(0, 10);
    Page<Mention> results = mentionService.getMyMentions(
        mentioned,
        project.getProjectId(),
        List.of(author.getUserId()),
        List.of(TargetType.POST),
        null,
        null,
        pageable);

    assertEquals(1, results.getTotalElements());
    assertEquals(project.getProjectId(), results.getContent().get(0).getProject().getProjectId());
    assertEquals(TargetType.POST, results.getContent().get(0).getTargetType());
  }

  @Test
  void getMyMentions_filtersByDateRange() {
    LocalDateTime now = LocalDateTime.now();
    saveMention(project, TargetType.POST, "1", mentioned, author, now.minusDays(2));
    saveMention(project, TargetType.TASK, "2", mentioned, author, now.minusHours(1));

    Pageable pageable = PageRequest.of(0, 10);
    Page<Mention> results = mentionService.getMyMentions(
        mentioned,
        null,
        null,
        null,
        now.minusDays(1),
        now,
        pageable);

    assertEquals(1, results.getTotalElements());
    assertEquals("2", results.getContent().get(0).getTargetId());
  }

  @Test
  void getMyMentions_deniesNonMemberWhenProjectFilterExists() {
    Project forbidden = ServiceTestDataFactory.createProject(projectJpaRepository, author, author, "Forbidden");
    saveMention(forbidden, TargetType.POST, "1", mentioned, author, LocalDateTime.now());

    Pageable pageable = PageRequest.of(0, 10);
    assertThrows(AccessDeniedException.class, () -> mentionService.getMyMentions(
        mentioned,
        forbidden.getProjectId(),
        null,
        null,
        null,
        null,
        pageable));
  }

  private void saveMention(
      Project targetProject,
      TargetType targetType,
      String targetId,
      User mentionedUser,
      User createdBy,
      LocalDateTime createdAt
  ) {
    Mention mention = new Mention();
    MentionMapper.applyCreate(mention, new MentionCreateRequest(
        targetProject.getProjectId(),
        targetType,
        targetId,
        mentionedUser.getUserId(),
        createdBy.getUserId()));
    mention.setProject(targetProject);
    mention.setMentionedUser(mentionedUser);
    mention.setCreatedBy(createdBy);
    mention.setCreatedAt(createdAt);
    mentionJpaRepository.save(mention);
  }
}

package com.onetuks.ihub.service.communication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.NoticeCreateRequest;
import com.onetuks.ihub.dto.communication.NoticeUpdateRequest;
import com.onetuks.ihub.entity.communication.Notice;
import com.onetuks.ihub.entity.communication.NoticeImportance;
import com.onetuks.ihub.entity.communication.NoticeStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.NoticeJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import java.time.LocalDateTime;
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
class NoticeServiceTest {

  @Autowired
  private NoticeService noticeService;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;

  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  private Project project;
  private User author;

  @BeforeEach
  void setUp() {
    author = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, author, author, "NoticeProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, author);
  }

  @Test
  void createNotice_success() {
    NoticeCreateRequest request = new NoticeCreateRequest("title", "content", "cat", "CRITICAL", true);

    Notice result = noticeService.create(author, project.getProjectId(), request);

    assertThat(result.getNoticeId()).isNotNull();
    assertThat(result.getTitle()).isEqualTo("title");
    assertThat(result.getImportance()).isEqualTo(NoticeImportance.CRITICAL);
    assertThat(result.getIsPinned()).isTrue();
    assertThat(result.getPinnedAt()).isNotNull();
  }

  @Test
  void getNotices_filtersKeywordAndPinned() {
    noticeService.create(author, project.getProjectId(), new NoticeCreateRequest("t1", "hello world", null, null, true));
    noticeService.create(author, project.getProjectId(), new NoticeCreateRequest("t2", "bye", null, null, false));
    Pageable pageable = PageRequest.of(0, 10);

    Page<Notice> results = noticeService.getAll(
        author,
        project.getProjectId(),
        "hello",
        null,
        null,
        null,
        true,
        null,
        null,
        pageable);

    assertThat(results.getTotalElements()).isEqualTo(1);
    assertThat(results.getContent().get(0).getTitle()).isEqualTo("t1");
  }

  @Test
  void updateNotice_success() {
    Notice created = noticeService.create(author, project.getProjectId(), new NoticeCreateRequest("t", "c", null, null, false));
    NoticeUpdateRequest update = new NoticeUpdateRequest("newT", "newC", "cat", "IMPORTANT", true);

    Notice updated = noticeService.update(author, created.getNoticeId(), update);

    assertThat(updated.getTitle()).isEqualTo("newT");
    assertThat(updated.getContent()).isEqualTo("newC");
    assertThat(updated.getImportance()).isEqualTo(NoticeImportance.IMPORTANT);
    assertThat(updated.getIsPinned()).isTrue();
    assertThat(updated.getPinnedAt()).isNotNull();
  }

  @Test
  void deleteNotice_marksDeleted() {
    Notice created = noticeService.create(author, project.getProjectId(), new NoticeCreateRequest("t", "c", null, null, false));

    Notice deleted = noticeService.delete(author, created.getNoticeId());

    assertThat(deleted.getStatus()).isEqualTo(NoticeStatus.DELETED);
    assertThat(deleted.getDeletedAt()).isNotNull();
  }

  @Test
  void accessDeniedForNonMember() {
    User outsider = ServiceTestDataFactory.createUser(userJpaRepository);
    Notice created = noticeService.create(author, project.getProjectId(), new NoticeCreateRequest("t", "c", null, null, false));

    assertThatThrownBy(() -> noticeService.getById(outsider, created.getNoticeId()))
        .isInstanceOf(AccessDeniedException.class);
  }
}

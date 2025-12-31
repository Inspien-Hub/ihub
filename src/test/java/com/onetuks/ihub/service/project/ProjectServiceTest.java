package com.onetuks.ihub.service.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectResponse;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.ProjectMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
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
class ProjectServiceTest {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  private User creator;
  private User hacker;

  @BeforeEach
  void setUp() {
    creator = userJpaRepository.save(buildUser("creator@example.com", "Creator"));
    hacker = userJpaRepository.save(buildUser("hacker@example.com", "Hacker"));
  }

  @Test
  void createProject_success() {
    ProjectCreateRequest request = createRequest("PRoject Create");

    Project result = projectService.create(creator, request);

    assertThat(result.getProjectId()).isNotNull();
    assertThat(result.getTitle()).isEqualToIgnoringCase(request.title());
    assertThat(result.getStatus()).isEqualTo(ProjectStatus.ACTIVE);
    assertThat(result.getCreatedBy().getUserId()).isEqualTo(creator.getUserId());
    assertThat(result.getUpdatedBy().getUserId()).isEqualTo(creator.getUserId());
  }

  @Test
  void updateProject_success() {
    // Given
    Project created = projectService.create(creator, createRequest("Project update"));

    ProjectUpdateRequest request = new ProjectUpdateRequest(
        "Project B Updated",
        "New Desc",
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(8),
        ProjectStatus.INACTIVE);

    // When
    Project result = projectService.update(creator, created.getProjectId(), request);

    // Then
    assertThat(result.getProjectId()).isNotNull();
    assertThat(result.getTitle()).isEqualToIgnoringCase(request.title());
    assertThat(result.getStatus()).isEqualTo(request.status());
    assertThat(result.getCreatedBy().getUserId()).isEqualTo(creator.getUserId());
  }

  @Test
  void getProject_exception() {
    // Given
    Project created = projectService.create(creator, createRequest("Project get exception"));

    // When & Then
    assertThatThrownBy(() -> projectService.getById(hacker, created.getProjectId()))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  void getProjects_returnsAll() {
    Pageable pageable = PageRequest.of(10, 10);
    long expected = projectJpaRepository.count() + 2;
    projectService.create(creator, createRequest("Project getAll1"));
    projectService.create(creator, createRequest("Project getAll2"));

    Page<Project> results = projectService.getAll(creator, pageable);

    assertThat(results.getTotalElements()).isEqualTo((int) expected);
  }

  @Test
  void deleteProject_success() {
    Project created = projectService.create(creator, createRequest("Project Delete"));

    Project result = projectService.delete(creator, created.getProjectId());

    assertThat(result.getStatus()).isEqualTo(ProjectStatus.DELETED);
  }

  private User buildUser(String email, String name) {
    User user = new User();
    user.setUserId(email);
    user.setEmail(email);
    user.setPassword("pass");
    user.setName(name);
    user.setStatus(UserStatus.ACTIVE);
    return user;
  }

  private ProjectCreateRequest createRequest(String title) {
    return new ProjectCreateRequest(
        title,
        "Desc",
        LocalDate.now(),
        LocalDate.now().plusDays(5),
        ProjectStatus.ACTIVE);
  }
}

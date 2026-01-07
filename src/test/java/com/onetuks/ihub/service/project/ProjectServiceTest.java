package com.onetuks.ihub.service.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.FavoriteProject;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import java.time.LocalDate;
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
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

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
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, created, creator);

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
    projectService.create(creator, createRequest("Project getAll1"));
    projectService.create(creator, createRequest("Project getAll2"));

    Page<Project> results = projectService.getAllMine(creator, pageable);

    assertThat(results.getTotalElements()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void deleteProject_success() {
    Project created = projectService.create(creator, createRequest("Project Delete"));
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, created, creator);

    Project result = projectService.delete(creator, created.getProjectId());

    assertThat(result.getStatus()).isEqualTo(ProjectStatus.DELETED);
  }

  @Test
  void manageFavorite_added() {
    // Given
    Project created = projectService.create(creator, createRequest("Project Delete"));

    // When
    FavoriteProject result = projectService.manageFavorite(creator, created.getProjectId());

    // Then
    assertThat(result.getFavoriteProjectId()).isNotNull();
    assertThat(result.getIsFavorite()).isTrue();
  }

  @Test
  void manageFavorite_changed() {
    // Given
    Project created = projectService.create(creator, createRequest("Project FAvorite"));
    projectService.manageFavorite(creator, created.getProjectId());

    // When
    FavoriteProject result = projectService.manageFavorite(creator, created.getProjectId());

    // Then
    assertThat(result.getIsFavorite()).isFalse();
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

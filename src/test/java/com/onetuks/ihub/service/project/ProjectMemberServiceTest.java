package com.onetuks.ihub.service.project;

import static org.assertj.core.api.Assertions.assertThat;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class ProjectMemberServiceTest {

  @Autowired
  private ProjectMemberService projectMemberService;

  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;
  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;

  private User owner;
  private Project project;

  @BeforeEach
  void setUp() {
    owner = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, owner, owner,
        "MemberProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, owner);
  }

  @Test
  void createProjectMember_defaultsToViewer() {
    User member = ServiceTestDataFactory.createUser(userJpaRepository);
    ProjectMemberCreateRequest request = new ProjectMemberCreateRequest(member.getUserId());

    ProjectMember result = projectMemberService.create(owner, project.getProjectId(), request);

    assertThat(result.getProjectMemberId()).isNotNull();
    assertThat(result.getRole()).isEqualTo(ProjectMemberRole.PROJECT_VIEWER);
  }

  @Test
  void updateProjectMember_success() {
    User member = ServiceTestDataFactory.createUser(userJpaRepository);
    ProjectMember created = projectMemberService.create(owner, project.getProjectId(),
        new ProjectMemberCreateRequest(member.getUserId()));
    ProjectMemberUpdateRequest updateRequest =
        new ProjectMemberUpdateRequest(ProjectMemberRole.PROJECT_MEMBER);

    ProjectMember result = projectMemberService.update(
        owner, project.getProjectId(), created.getProjectMemberId(), updateRequest);

    assertThat(result.getRole()).isEqualTo(ProjectMemberRole.PROJECT_MEMBER);
    assertThat(result.getLeftAt()).isNull();
  }

  @Test
  void updateProjectMember_viewer_success() {
    User member = ServiceTestDataFactory.createUser(userJpaRepository);
    ProjectMember created = projectMemberService.create(owner, project.getProjectId(),
        new ProjectMemberCreateRequest(member.getUserId()));
    projectMemberService.update(
        owner, project.getProjectId(), created.getProjectMemberId(),
        new ProjectMemberUpdateRequest(ProjectMemberRole.PROJECT_MEMBER));

    ProjectMember result = projectMemberService.update(
        owner, project.getProjectId(), created.getProjectMemberId(),
        new ProjectMemberUpdateRequest(ProjectMemberRole.PROJECT_VIEWER));

    assertThat(result.getRole()).isEqualTo(ProjectMemberRole.PROJECT_VIEWER);
    assertThat(result.getLeftAt()).isNotNull();
  }

  @Transactional
  @Test
  void getProjectMembers_excludesDeletedUsers() {
    User active = ServiceTestDataFactory.createUser(userJpaRepository);
    User deleted = ServiceTestDataFactory.createUser(userJpaRepository);
    deleted.setStatus(UserStatus.DELETED);
    userJpaRepository.save(deleted);

    projectMemberService.create(owner, project.getProjectId(),
        new ProjectMemberCreateRequest(active.getUserId()));
    projectMemberService.create(owner, project.getProjectId(),
        new ProjectMemberCreateRequest(deleted.getUserId()));

    Pageable pageable = PageRequest.of(0, 10);
    Page<ProjectMember> results =
        projectMemberService.getAll(owner, project.getProjectId(), pageable);

    assertThat(results.getContent()).hasSize(2);
    assertThat(results.getContent().stream()
        .noneMatch(member -> member.getUser().getStatus() == UserStatus.DELETED))
        .isTrue();
  }

  @Test
  void deleteProjectMember_success() {
    User member = ServiceTestDataFactory.createUser(userJpaRepository);
    ProjectMember created = projectMemberService.create(owner, project.getProjectId(),
        new ProjectMemberCreateRequest(member.getUserId()));

    projectMemberService.delete(owner, project.getProjectId(), created.getProjectMemberId());

    assertThat(projectMemberJpaRepository.findById(created.getProjectMemberId())).isEmpty();
  }
}

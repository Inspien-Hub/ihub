package com.onetuks.ihub.service.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.system.SystemOwnerCreateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.SystemOwner;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.repository.SystemOwnerJpaRepository;
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
class SystemOwnerServiceTest {

  @Autowired
  private SystemOwnerService systemOwnerService;

  @Autowired
  private SystemOwnerJpaRepository systemOwnerJpaRepository;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;
  @Autowired
  private SystemJpaRepository systemJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;

  private User creator;
  private User owner;
  private Project project;
  private com.onetuks.ihub.entity.system.System system;

  @BeforeEach
  void setUp() {
    creator = ServiceTestDataFactory.createUser(userJpaRepository);
    owner = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, creator, creator,
        "OwnerProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, creator);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, owner);
    system = ServiceTestDataFactory.createSystem(systemJpaRepository, project, creator, creator,
        "SYS-OWN");
  }

  @Test
  void createSystemOwner_success() {
    SystemOwnerCreateRequest request = new SystemOwnerCreateRequest(
        system.getSystemId(), owner.getUserId());

    SystemOwner result = systemOwnerService.create(creator, project.getProjectId(), request);

    assertThat(result.getSystemOwnerId()).isNotNull();
    assertThat(result.getUser().getUserId()).isEqualTo(owner.getUserId());
    assertThat(result.getSystem().getSystemId()).isEqualTo(system.getSystemId());
    assertThat(result.getCreatedAt()).isNotNull();
  }

  @Test
  void getSystemOwners_filtersByUser() {
    User other = ServiceTestDataFactory.createUser(userJpaRepository);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, other);
    Pageable pageable = PageRequest.of(0, 10);

    systemOwnerService.create(creator, project.getProjectId(), new SystemOwnerCreateRequest(
        system.getSystemId(), owner.getUserId()));
    systemOwnerService.create(creator, project.getProjectId(), new SystemOwnerCreateRequest(
        system.getSystemId(), other.getUserId()));

    Page<SystemOwner> all = systemOwnerService.getAll(
        creator, project.getProjectId(), null, pageable);
    Page<SystemOwner> filtered = systemOwnerService.getAll(
        creator, project.getProjectId(), owner.getUserId(), pageable);

    assertThat(all.getTotalElements()).isEqualTo(2);
    assertThat(filtered.getTotalElements()).isEqualTo(1);
    assertThat(filtered.getContent().getFirst().getUser().getUserId())
        .isEqualTo(owner.getUserId());
  }

  @Test
  void deleteSystemOwner_success() {
    SystemOwner created = systemOwnerService.create(creator, project.getProjectId(),
        new SystemOwnerCreateRequest(system.getSystemId(), owner.getUserId()));

    systemOwnerService.delete(creator, created.getSystemOwnerId());

    assertThat(systemOwnerJpaRepository.findById(created.getSystemOwnerId())).isEmpty();
  }

  @Test
  void getSystemOwners_deniedForNonMember() {
    User outsider = ServiceTestDataFactory.createUser(userJpaRepository);
    Pageable pageable = PageRequest.of(0, 10);

    assertThatThrownBy(() -> systemOwnerService.getAll(
        outsider, project.getProjectId(), null, pageable))
        .isInstanceOf(AccessDeniedException.class);
  }
}

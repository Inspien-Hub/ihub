package com.onetuks.ihub.service.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransition;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.InterfaceStatusJpaRepository;
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
class InterfaceStatusTransitionServiceTest {

  @Autowired
  private InterfaceStatusTransitionService interfaceStatusTransitionService;

  @Autowired
  private InterfaceStatusJpaRepository interfaceStatusJpaRepository;
  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  private Project project;
  private User user;
  private InterfaceStatus fromStatus;
  private InterfaceStatus toStatus;

  @BeforeEach
  void setUp() {
    user = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, user, user, "IFTranProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, user);
    fromStatus =
        ServiceTestDataFactory.createInterfaceStatus(interfaceStatusJpaRepository, project, "Draft",
            1);
    toStatus =
        ServiceTestDataFactory.createInterfaceStatus(interfaceStatusJpaRepository, project, "Live",
            2);
  }

  private InterfaceStatusTransitionCreateRequest buildCreateRequest() {
    return new InterfaceStatusTransitionCreateRequest(
        project.getProjectId(),
        fromStatus.getStatusId(),
        toStatus.getStatusId(),
        InterfaceRole.ADMIN);
  }

  @Test
  void createInterfaceStatusTransition_success() {
    InterfaceStatusTransitionCreateRequest request = buildCreateRequest();

    InterfaceStatusTransition result = interfaceStatusTransitionService.create(user, request);

    assertThat(result.getTransitionId()).isNotNull();
    assertThat(result.getStatus()).isEqualTo(InterfaceStatusTransitionStatus.ACTIVE);
    assertThat(result.getAllowedRole()).isEqualTo(InterfaceRole.ADMIN);
  }

  @Test
  void updateInterfaceStatusTransition_success() {
    InterfaceStatusTransition created =
        interfaceStatusTransitionService.create(user, buildCreateRequest());

    InterfaceStatusTransitionUpdateRequest updateRequest =
        new InterfaceStatusTransitionUpdateRequest(
            fromStatus.getStatusId(),
            toStatus.getStatusId(),
            InterfaceRole.MEMBER,
            InterfaceStatusTransitionStatus.INACTIVE);

    InterfaceStatusTransition result =
        interfaceStatusTransitionService.update(user, created.getTransitionId(), updateRequest);

    assertThat(result.getAllowedRole()).isEqualTo(InterfaceRole.MEMBER);
    assertThat(result.getStatus()).isEqualTo(InterfaceStatusTransitionStatus.INACTIVE);
  }

  @Test
  void getInterfaceStatusTransitions_returnsAll() {
    Pageable pageable = PageRequest.of(0, 10);
    interfaceStatusTransitionService.create(user, buildCreateRequest());
    interfaceStatusTransitionService.create(user, buildCreateRequest());

    Page<InterfaceStatusTransition> results = interfaceStatusTransitionService.getAll(pageable);

    assertThat(results.getTotalElements()).isGreaterThanOrEqualTo(2);
  }
}

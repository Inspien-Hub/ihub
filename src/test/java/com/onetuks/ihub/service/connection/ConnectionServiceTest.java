package com.onetuks.ihub.service.connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.system.ConnectionCreateRequest;
import com.onetuks.ihub.dto.system.ConnectionUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.Connection;
import com.onetuks.ihub.entity.system.ConnectionStatus;
import com.onetuks.ihub.entity.system.Protocol;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ConnectionJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import com.onetuks.ihub.service.system.ConnectionService;
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
class ConnectionServiceTest {

  @Autowired
  private ConnectionService connectionService;

  @Autowired
  private ConnectionJpaRepository connectionJpaRepository;
  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private SystemJpaRepository systemJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  private User creator;
  private com.onetuks.ihub.entity.system.System system;

  @BeforeEach
  void setUp() {
    creator = ServiceTestDataFactory.createUser(userJpaRepository);
    User updater = ServiceTestDataFactory.createUser(userJpaRepository);
    Project project = ServiceTestDataFactory.createProject(
        projectJpaRepository, creator, updater, "ConnProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, creator);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, updater);
    system = ServiceTestDataFactory.createSystem(
        systemJpaRepository, project, creator, updater, "SYS-CONN");
  }

  @Test
  void createConnection_success() {
    ConnectionCreateRequest request = buildCreateRequest();

    Connection result = connectionService.create(creator, system.getSystemId(), request);

    assertThat(result.getConnectionId()).isNotNull();
    assertThat(result.getName()).isEqualTo(request.name());
    assertThat(result.getStatus()).isEqualTo(ConnectionStatus.INACTIVE);
  }

  @Test
  void updateConnection_success() {
    Connection created =
        connectionService.create(creator, system.getSystemId(), buildCreateRequest());

    ConnectionUpdateRequest updateRequest = new ConnectionUpdateRequest(
        "Conn2-Updated",
        Protocol.SFTP,
        "example.com",
        22,
        "/updated",
        "updatedUser",
        "KEY",
        "{\"new\":\"json\"}",
        ConnectionStatus.ACTIVE,
        "new desc");

    Connection result = connectionService.update(creator, created.getConnectionId(), updateRequest);

    assertThat(result.getName()).isEqualTo(updateRequest.name());
    assertThat(result.getStatus()).isEqualTo(updateRequest.status());
  }

  @Test
  void getConnections_returnsAll() {
    Pageable pageable = PageRequest.of(0, 10);
    connectionService.create(creator, system.getSystemId(), buildCreateRequest());
    connectionService.create(creator, system.getSystemId(), buildCreateRequest());

    Page<Connection> results = connectionService.getAll(creator, system.getSystemId(), pageable);

    assertThat(results.getTotalElements()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void getConnection_exception() {
    // Given
    User hacker = ServiceTestDataFactory.createUser(userJpaRepository);
    Connection created =
        connectionService.create(creator, system.getSystemId(), buildCreateRequest());

    // When & Then
    assertThatThrownBy(() -> connectionService.getById(hacker, created.getConnectionId()))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  void deleteConnection_success() {
    Connection created = connectionService.create(creator, system.getSystemId(),
        buildCreateRequest());

    Connection result = connectionService.delete(creator, created.getConnectionId());

    assertThat(result.getStatus()).isEqualTo(ConnectionStatus.DELETED);
  }

  private ConnectionCreateRequest buildCreateRequest() {
    return new ConnectionCreateRequest(
        "Conn1",
        Protocol.HTTP,
        "localhost",
        8080,
        "/path",
        "user",
        "BASIC",
        "{\"k\":\"v\"}",
        "desc");
  }
}

package com.onetuks.ihub.service.system;

import com.onetuks.ihub.dto.system.ConnectionCreateRequest;
import com.onetuks.ihub.dto.system.ConnectionUpdateRequest;
import com.onetuks.ihub.entity.system.Connection;
import com.onetuks.ihub.entity.system.ConnectionStatus;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.ConnectionMapper;
import com.onetuks.ihub.repository.ConnectionJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectionService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final ConnectionJpaRepository connectionRepository;
  private final SystemJpaRepository systemRepository;

  @Transactional(readOnly = true)
  public Page<Connection> getAll(User currentUser, String systemId, Pageable pageable) {
    System system = findSystem(systemId);
    projectMemberCheckComponent.checkIsProjectViewer(
        currentUser.getUserId(), system.getProject().getProjectId());
    return connectionRepository.findAllBySystem_SystemId(systemId, pageable);
  }

  @Transactional
  public Connection create(User currentUser, String systemId, ConnectionCreateRequest request) {
    System system = findSystem(systemId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), system.getProject().getProjectId());
    return connectionRepository.save(
        ConnectionMapper.applyCreate(system, currentUser, request));
  }

  @Transactional(readOnly = true)
  public Connection getById(User currentUser, String connectionId) {
    Connection connection = findEntity(connectionId);
    projectMemberCheckComponent.checkIsProjectViewer(
        currentUser.getUserId(), connection.getSystem().getProject().getProjectId());
    return connection;
  }

  @Transactional
  public Connection update(User currentUser, String connectionId, ConnectionUpdateRequest request) {
    Connection connection = findEntity(connectionId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), connection.getSystem().getProject().getProjectId());
    return ConnectionMapper.applyUpdate(connection, currentUser, request);
  }

  @Transactional
  public Connection delete(User currentUser, String connectionId) {
    Connection connection = findEntity(connectionId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), connection.getSystem().getProject().getProjectId());
    connection.setStatus(ConnectionStatus.DELETED);
    connection.setUpdatedBy(currentUser);
    connection.setUpdatedAt(LocalDateTime.now());
    return connection;
  }

  private Connection findEntity(String connectionId) {
    return connectionRepository.findById(connectionId)
        .orElseThrow(() -> new EntityNotFoundException("Connection not found: " + connectionId));
  }

  private System findSystem(String systemId) {
    return systemRepository.findById(systemId)
        .orElseThrow(() -> new EntityNotFoundException("System not found: " + systemId));
  }
}

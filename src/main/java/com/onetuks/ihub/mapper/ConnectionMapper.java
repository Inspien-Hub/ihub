package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.system.ConnectionCreateRequest;
import com.onetuks.ihub.dto.system.ConnectionResponse;
import com.onetuks.ihub.dto.system.ConnectionUpdateRequest;
import com.onetuks.ihub.entity.system.Connection;
import com.onetuks.ihub.entity.system.ConnectionStatus;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ConnectionMapper {

  private ConnectionMapper() {
  }

  public static ConnectionResponse toResponse(Connection connection) {
    var project = Objects.requireNonNull(connection.getSystem().getProject());
    var system = Objects.requireNonNull(connection.getSystem());
    var createdBy = Objects.requireNonNull(connection.getCreatedBy());
    var updatedBy = Objects.requireNonNull(connection.getUpdatedBy());
    return new ConnectionResponse(
        connection.getConnectionId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        system.getSystemId(),
        connection.getName(),
        connection.getProtocol(),
        connection.getHost(),
        connection.getPort(),
        connection.getPath(),
        connection.getUsername(),
        connection.getAuthType(),
        connection.getExtraConfig(),
        connection.getStatus(),
        connection.getDescription(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        updatedBy.getUserId(),
        updatedBy.getStatus(),
        updatedBy.getName(),
        connection.getCreatedAt(),
        connection.getUpdatedAt());
  }

  public static Connection applyCreate(
      System system, User currentUser, ConnectionCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    return new Connection(
        UUIDProvider.provideUUID(Connection.TABLE_NAME),
        system,
        request.name(),
        request.protocol(),
        request.host(),
        request.port(),
        request.path(),
        request.username(),
        request.authType(),
        request.extraConfig(),
        ConnectionStatus.INACTIVE,
        request.description(),
        currentUser,
        currentUser,
        now,
        now
    );
  }

  public static Connection applyUpdate(
      Connection connection, User currentUser, ConnectionUpdateRequest request) {
    if (request.name() != null) {
      connection.setName(request.name());
    }
    if (request.protocol() != null) {
      connection.setProtocol(request.protocol());
    }
    if (request.host() != null) {
      connection.setHost(request.host());
    }
    if (request.port() != null) {
      connection.setPort(request.port());
    }
    if (request.path() != null) {
      connection.setPath(request.path());
    }
    if (request.username() != null) {
      connection.setUsername(request.username());
    }
    if (request.authType() != null) {
      connection.setAuthType(request.authType());
    }
    if (request.extraConfig() != null) {
      connection.setExtraConfig(request.extraConfig());
    }
    if (request.status() != null) {
      connection.setStatus(request.status());
    }
    if (request.description() != null) {
      connection.setDescription(request.description());
    }
    connection.setUpdatedAt(LocalDateTime.now());
    connection.setUpdatedBy(currentUser);
    return connection;
  }
}

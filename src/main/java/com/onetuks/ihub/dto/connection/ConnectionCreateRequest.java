package com.onetuks.ihub.dto.connection;

import com.onetuks.ihub.entity.connection.ConnectionStatus;
import com.onetuks.ihub.entity.connection.Protocol;
import jakarta.validation.constraints.NotNull;

public record ConnectionCreateRequest(
    @NotNull Long projectId,
    @NotNull Long systemId,
    String name,
    Protocol protocol,
    String host,
    Integer port,
    String path,
    String username,
    String authType,
    String extraConfig,
    @NotNull ConnectionStatus status,
    String description,
    @NotNull Long createdById,
    @NotNull Long updatedById
) {
}

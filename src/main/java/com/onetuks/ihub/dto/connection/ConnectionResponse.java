package com.onetuks.ihub.dto.connection;

import com.onetuks.ihub.entity.connection.ConnectionStatus;
import com.onetuks.ihub.entity.connection.Protocol;
import java.time.LocalDateTime;

public record ConnectionResponse(
    String connectionId,
    String projectId,
    String systemId,
    String name,
    Protocol protocol,
    String host,
    Integer port,
    String path,
    String username,
    String authType,
    String extraConfig,
    ConnectionStatus status,
    String description,
    String createdById,
    String updatedById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

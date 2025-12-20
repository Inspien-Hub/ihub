package com.onetuks.ihub.dto.connection;

import com.onetuks.ihub.entity.connection.ConnectionStatus;
import com.onetuks.ihub.entity.connection.Protocol;
import java.time.LocalDateTime;

public record ConnectionResponse(
    Long connectionId,
    Long projectId,
    Long systemId,
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
    Long createdById,
    Long updatedById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.system.Protocol;

public record ConnectionCreateRequest(
    String name,
    Protocol protocol,
    String host,
    Integer port,
    String path,
    String username,
    String authType,
    String extraConfig,
    String description
) {

}

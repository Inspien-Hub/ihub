package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.system.SystemOwnerResponse;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.system.SystemOwner;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class SystemOwnerMapper {

  private SystemOwnerMapper() {
  }

  public static SystemOwnerResponse toResponse(SystemOwner systemOwner) {
    var project = Objects.requireNonNull(systemOwner.getProject());
    var system = Objects.requireNonNull(systemOwner.getSystem());
    var user = Objects.requireNonNull(systemOwner.getUser());
    return new SystemOwnerResponse(
        systemOwner.getSystemOwnerId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        system.getSystemId(),
        system.getSystemCode(),
        user.getUserId(),
        user.getStatus(),
        user.getName(),
        systemOwner.getCreatedAt()
    );
  }

  public static SystemOwner applyCreate(Project project, System system, User user) {
    return new SystemOwner(
        UUIDProvider.provideUUID(SystemOwner.TABLE_NAME),
        project,
        system,
        user,
        LocalDateTime.now()
    );
  }
}

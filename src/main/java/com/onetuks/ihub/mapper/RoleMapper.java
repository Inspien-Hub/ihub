package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.role.RoleCreateRequest;
import com.onetuks.ihub.dto.role.RoleGrantResponse;
import com.onetuks.ihub.dto.role.RoleResponse;
import com.onetuks.ihub.dto.role.RoleRevokeResponse;
import com.onetuks.ihub.dto.role.RoleUpdateRequest;
import com.onetuks.ihub.entity.role.Role;
import java.util.List;

public final class RoleMapper {

  private RoleMapper() {
  }

  public static RoleResponse toResponse(Role role) {
    return new RoleResponse(role.getRoleId(), role.getRoleName(), role.getDescription());
  }

  public static Role applyCreate(RoleCreateRequest request) {
    return new Role(
        UUIDProvider.provideUUID(Role.TABLE_NAME),
        request.roleName(),
        request.description()
    );
  }

  public static Role applyUpdate(Role role, RoleUpdateRequest request) {
    if (request.roleName() != null) {
      role.setRoleName(request.roleName());
    }
    if (request.description() != null) {
      role.setDescription(request.description());
    }
    return role;
  }

  public static RoleGrantResponse toGrantResponse(String email, List<Role> roles) {
    return new RoleGrantResponse(email, roles.stream().map(Role::getRoleName).toList());
  }

  public static RoleRevokeResponse toRevokeResponse(String email, List<Role> revoke) {
    return new RoleRevokeResponse(email, revoke.stream().map(Role::getRoleName).toList());
  }
}

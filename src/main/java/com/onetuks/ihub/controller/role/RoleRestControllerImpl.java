package com.onetuks.ihub.controller.role;

import static com.onetuks.ihub.config.RoleDataInitializer.USER_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.role.RoleCreateRequest;
import com.onetuks.ihub.dto.role.RoleGrantRequest;
import com.onetuks.ihub.dto.role.RoleGrantResponse;
import com.onetuks.ihub.dto.role.RoleResponse;
import com.onetuks.ihub.dto.role.RoleRevokeRequest;
import com.onetuks.ihub.dto.role.RoleRevokeResponse;
import com.onetuks.ihub.dto.role.RoleUpdateRequest;
import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.mapper.RoleMapper;
import com.onetuks.ihub.service.role.RoleService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleRestControllerImpl implements RoleRestController {

  private final RoleService roleService;

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<RoleGrantResponse> grantRoleToUser(
      @Valid @RequestBody RoleGrantRequest request
  ) {
    List<Role> results = roleService.grant(request);
    RoleGrantResponse response = RoleMapper.toGrantResponse(request.email(), results);
    return ResponseEntity.ok(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<RoleRevokeResponse> revokeRoleFromUser(
      @Valid @RequestBody RoleRevokeRequest request
  ) {
    List<Role> results = roleService.revoke(request);
    RoleRevokeResponse response = RoleMapper.toRevokeResponse(request.email(), results);
    return ResponseEntity.ok(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<RoleResponse> createRole(
      @Valid @RequestBody RoleCreateRequest request
  ) {
    Role result = roleService.create(request);
    RoleResponse response = RoleMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/roles/" + response.roleId()))
        .body(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<RoleResponse> getRole(
      @PathVariable String roleId
  ) {
    Role result = roleService.getById(roleId);
    RoleResponse response = RoleMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<List<RoleResponse>> getRoles() {
    List<Role> results = roleService.getAll();
    List<RoleResponse> responses = results.stream().map(RoleMapper::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<RoleResponse> updateRole(
      @PathVariable String roleId,
      @Valid @RequestBody RoleUpdateRequest request
  ) {
    return ResponseEntity.ok(RoleMapper.toResponse(roleService.update(roleId, request)));
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<Void> deleteRole(@PathVariable String roleId) {
    roleService.delete(roleId);
    return ResponseEntity.noContent().build();
  }
}

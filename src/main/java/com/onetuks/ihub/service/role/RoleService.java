package com.onetuks.ihub.service.role;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.config.RoleDataInitializer;
import com.onetuks.ihub.dto.role.RoleCreateRequest;
import com.onetuks.ihub.dto.role.RoleGrantRequest;
import com.onetuks.ihub.dto.role.RoleRevokeRequest;
import com.onetuks.ihub.dto.role.RoleUpdateRequest;
import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.RoleMapper;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.RoleJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.repository.UserRoleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleJpaRepository roleRepository;
  private final UserRoleJpaRepository userRoleRepository;
  private final UserJpaRepository userRepository;

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public List<Role> grant(User user, RoleGrantRequest request) {
    List<UserRole> originUserRoles = userRoleRepository.findAllByUserEmail(request.email());
    List<UserRole> grantedUserRoles = request.roleIds().stream()
        .map(roleRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(role -> originUserRoles.stream()
            .noneMatch(userRole -> Objects.equals(userRole.getRole(), role)))
        .map(role -> userRoleRepository.save(
            new UserRole(
                UUIDProvider.provideUUID(UserRole.TABLE_NAME),
                userRepository.findById(request.email()).orElseThrow(EntityNotFoundException::new),
                role)))
        .toList();

    List<Role> allUsersRoles = new ArrayList<>();
    allUsersRoles.addAll(originUserRoles.stream().map(UserRole::getRole).toList());
    allUsersRoles.addAll(grantedUserRoles.stream().map(UserRole::getRole).toList());
    return allUsersRoles;
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public List<Role> revoke(User user, RoleRevokeRequest request) {
    List<UserRole> originUserRoles = userRoleRepository.findAllByUserEmail(request.email());
    request.roleIds().stream()
        .map(roleRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(role -> originUserRoles.stream()
            .anyMatch(userRole -> Objects.equals(userRole.getRole(), role)))
        .forEach(role -> userRoleRepository.deleteByUserEmailAndRole(request.email(), role));

    return originUserRoles.stream()
        .map(UserRole::getRole)
        .filter(role -> request.roleIds().stream()
            .noneMatch(roleId -> Objects.equals(roleId, role.getRoleId())))
        .toList();
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public Role create(User user, RoleCreateRequest request) {
    Role role = new Role();
    RoleMapper.applyCreate(role, request);
    return roleRepository.save(role);
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional(readOnly = true)
  public Role getById(User user, String roleId) {
    return findEntity(roleId);
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional(readOnly = true)
  public List<Role> getAll(User user) {
    return roleRepository.findAll();
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public Role update(User user, String roleId, RoleUpdateRequest request) {
    Role role = findEntity(roleId);
    RoleMapper.applyUpdate(role, request);
    return role;
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public void delete(User user, String roleId) {
    Role role = findEntity(roleId);
    roleRepository.delete(role);
  }

  private Role findEntity(String roleId) {
    return roleRepository.findById(roleId)
        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
  }
}

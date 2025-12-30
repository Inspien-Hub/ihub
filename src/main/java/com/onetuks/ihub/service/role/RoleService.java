package com.onetuks.ihub.service.role;

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

  @Transactional
  public List<Role> grant(RoleGrantRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.email()));
    List<UserRole> originUserRoles = userRoleRepository.findAllByUser(user);
    List<UserRole> grantedUserRoles = request.roleIds().stream()
        .map(roleRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(role -> originUserRoles.stream()
            .noneMatch(userRole -> Objects.equals(userRole.getRole(), role)))
        .map(role -> userRoleRepository.save(
            new UserRole(
                UUIDProvider.provideUUID(UserRole.TABLE_NAME),
                user,
                role)))
        .toList();

    List<Role> allUsersRoles = new ArrayList<>();
    allUsersRoles.addAll(originUserRoles.stream().map(UserRole::getRole).toList());
    allUsersRoles.addAll(grantedUserRoles.stream().map(UserRole::getRole).toList());
    return allUsersRoles;
  }

  @Transactional
  public List<Role> revoke(RoleRevokeRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.email()));
    List<UserRole> originUserRoles = userRoleRepository.findAllByUser(user);
    request.roleIds().stream()
        .map(roleRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(role -> originUserRoles.stream()
            .anyMatch(userRole -> Objects.equals(userRole.getRole(), role)))
        .forEach(role -> userRoleRepository.deleteByUserAndRole(user, role));

    return originUserRoles.stream()
        .map(UserRole::getRole)
        .filter(role -> request.roleIds().stream()
            .noneMatch(roleId -> Objects.equals(roleId, role.getRoleId())))
        .toList();
  }

  @Transactional
  public Role create(RoleCreateRequest request) {
    return roleRepository.save(RoleMapper.applyCreate(request));
  }

  @Transactional(readOnly = true)
  public Role getById(String roleId) {
    return findEntity(roleId);
  }

  @Transactional(readOnly = true)
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  @Transactional
  public Role update(String roleId, RoleUpdateRequest request) {
    return RoleMapper.applyUpdate(findEntity(roleId), request);
  }

  @Transactional
  public void delete(String roleId) {
    userRoleRepository.deleteAllByRole_RoleId(roleId);
    roleRepository.deleteById(roleId);
  }

  private Role findEntity(String roleId) {
    return roleRepository.findById(roleId)
        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
  }
}

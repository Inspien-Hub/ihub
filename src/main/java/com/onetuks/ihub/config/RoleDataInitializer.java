package com.onetuks.ihub.config;

import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.RoleJpaRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements ApplicationRunner {

  public static final String USER_FULL_ACCESS = "USER_FULL_ACCESS";
  public static final String PROJECT_FULL_ACCESS = "PROJECT_FULL_ACCESS";
  public static final String SYSTEM_FULL_ACCESS = "SYSTEM_FULL_ACCESS";
  public static final String TASK_FULL_ACCESS = "TASK_FULL_ACCESS";
  public static final String POST_FULL_ACCESS = "POST_FULL_ACCESS";
  public static final Map<String, String> PREPARED_ROLES = Map.of(
      USER_FULL_ACCESS, "계정 다건조회/단건조회/생성/수정",
      PROJECT_FULL_ACCESS, "프로젝트 단건조회/생성/수정/권한수정 (내 플젝만 가능), 멤버삭제",
      SYSTEM_FULL_ACCESS, "시스템 다건조회/생성/수정/삭제(내 플젝만 가능)",
      TASK_FULL_ACCESS, "일감 다건조회/단건조회",
      POST_FULL_ACCESS, "다건조회/단건조회/생성/수정/삭제 (내것만)"
  );

  private final RoleJpaRepository roleJpaRepository;

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    List<Role> allRoles = roleJpaRepository.findAll();
    PREPARED_ROLES.entrySet().stream()
        .filter(entrySet -> allRoles.stream()
            .noneMatch(role -> Objects.equals(role.getRoleName(), entrySet.getKey())))
        .forEach(
            entrySet -> roleJpaRepository.save(createRole(entrySet.getKey(), entrySet.getValue())));
  }

  private Role createRole(String roleName, String description) {
    Role role = new Role();
    role.setRoleId(UUIDProvider.provideUUID(Role.TABLE_NAME));
    role.setRoleName(roleName);
    role.setDescription(description);
    return role;
  }
}

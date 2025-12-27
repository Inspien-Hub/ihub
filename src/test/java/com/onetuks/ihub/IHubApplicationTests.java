package com.onetuks.ihub;

import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.RoleJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.repository.UserRoleJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import com.onetuks.ihub.service.role.RoleService;
import com.onetuks.ihub.service.user.UserService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class IHubApplicationTests {

  @Autowired
  public UserService userService;
  @Autowired
  public RoleService roleService;

  @Autowired
  public RoleJpaRepository roleJpaRepository;
  @Autowired
  public UserJpaRepository userJpaRepository;
  @Autowired
  public UserRoleJpaRepository userRoleJpaRepository;

  public final User preparedUser = ServiceTestDataFactory.createUser(userJpaRepository);
  public final List<Role> preparedRoles = ServiceTestDataFactory.createRoles(roleJpaRepository);
  public final List<UserRole> preparedUserRoles = ServiceTestDataFactory.createUserRole(
      userRoleJpaRepository, preparedUser, preparedRoles);

  @Test
  void contextLoads() {
  }

}

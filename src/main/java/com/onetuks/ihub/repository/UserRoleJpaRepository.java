package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, String> {

  List<UserRole> findAllByUser(User user);

  void deleteByUserAndRole(User user, Role role);

  void deleteAllByRole(Role role);
}

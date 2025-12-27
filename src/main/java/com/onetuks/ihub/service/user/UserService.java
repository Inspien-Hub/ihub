package com.onetuks.ihub.service.user;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.config.RoleDataInitializer;
import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
import com.onetuks.ihub.mapper.UserMapper;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserJpaRepository userJpaRepository;

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public User create(User user, UserCreateRequest request) {
    User newUser = new User();
    UserMapper.applyCreate(newUser, request);
    return userJpaRepository.save(newUser);
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional(readOnly = true)
  public User getById(User user, String email) {
    return findEntity(email);
  }

  @Transactional(readOnly = true)
  public List<User> getAll() {
    return userJpaRepository.findAll();
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public User update(User user, String email, UserUpdateRequest request) {
    User target = findEntity(email);
    UserMapper.applyUpdate(target, request);
    return target;
  }

  @RequiresRole(RoleDataInitializer.USER_FULL_ACCESS)
  @Transactional
  public User delete(User user, String email) {
    User target = findEntity(email);
    target.setStatus(UserStatus.DELETED);
    return target;
  }

  private User findEntity(String email) {
    return userJpaRepository.findById(email)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
  }
}

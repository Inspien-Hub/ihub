package com.onetuks.ihub.service.user;

import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import com.onetuks.ihub.entity.user.User;
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

  @Transactional
  public User create(UserCreateRequest request) {
    User user = new User();
    UserMapper.applyCreate(user, request);
    return userJpaRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User getById(Long userId) {
    return findEntity(userId);
  }

  @Transactional(readOnly = true)
  public List<User> getAll() {
    return userJpaRepository.findAll();
  }

  @Transactional
  public User update(Long userId, UserUpdateRequest request) {
    User user = findEntity(userId);
    UserMapper.applyUpdate(user, request);
    return user;
  }

  @Transactional
  public void delete(Long userId) {
    User user = findEntity(userId);
    userJpaRepository.delete(user);
  }

  private User findEntity(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

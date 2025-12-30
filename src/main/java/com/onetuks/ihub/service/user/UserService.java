package com.onetuks.ihub.service.user;

import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
import com.onetuks.ihub.mapper.UserMapper;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserJpaRepository userJpaRepository;

  @Transactional
  public User create(UserCreateRequest request) {
    return userJpaRepository.save(UserMapper.applyCreate(request));
  }

  @Transactional(readOnly = true)
  public User getById(String userId) {
    return findEntity(userId);
  }

  @Transactional(readOnly = true)
  public Page<User> getAll(Pageable pageable) {
    return userJpaRepository.findAll(pageable);
  }

  @Transactional
  public User update(String userId, UserUpdateRequest request) {
    return UserMapper.applyUpdate(findEntity(userId), request);
  }

  @Transactional
  public User delete(String userId) {
    User target = findEntity(userId);
    target.setStatus(UserStatus.DELETED);
    return target;
  }

  private User findEntity(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

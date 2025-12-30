package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserResponse;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;

public final class UserMapper {

  private UserMapper() {
  }

  public static UserResponse toResponse(User user) {
    return new UserResponse(
        user.getUserId(),
        user.getEmail(),
        user.getName(),
        user.getCompany(),
        user.getPosition(),
        user.getPhoneNumber(),
        user.getProfileImageUrl(),
        user.getStatus(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }

  public static User applyCreate(UserCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    return new User(
        request.email(),
        request.email(),
        request.password(),
        request.name(),
        request.company(),
        request.position(),
        request.phoneNumber(),
        request.profileImageUrl(),
        request.status(),
        now,
        now
    );
  }

  public static User applyUpdate(User user, UserUpdateRequest request) {
    if (request.password() != null) {
      user.setPassword(request.password());
    }
    if (request.name() != null) {
      user.setName(request.name());
    }
    if (request.company() != null) {
      user.setCompany(request.company());
    }
    if (request.position() != null) {
      user.setPosition(request.position());
    }
    if (request.phoneNumber() != null) {
      user.setPhoneNumber(request.phoneNumber());
    }
    if (request.profileImageUrl() != null) {
      user.setProfileImageUrl(request.profileImageUrl());
    }
    if (request.status() != null) {
      user.setStatus(request.status());
    }
    user.setUpdatedAt(LocalDateTime.now());
    return user;
  }
}

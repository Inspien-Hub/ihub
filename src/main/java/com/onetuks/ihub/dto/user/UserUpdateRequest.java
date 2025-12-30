package com.onetuks.ihub.dto.user;

import com.onetuks.ihub.entity.user.UserStatus;
import org.jspecify.annotations.Nullable;

public record UserUpdateRequest(
    String password,
    String name,
    String company,
    String position,
    String phoneNumber,
    String profileImageUrl,
    UserStatus status
) {

  public UserUpdateRequest applyEncodedPassword(String encodedPassword) {
    return new  UserUpdateRequest(
        encodedPassword,
        name,
        company,
        position,
        phoneNumber,
        profileImageUrl,
        status
    );
  }
}

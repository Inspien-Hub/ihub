package com.onetuks.ihub.dto.user;

import com.onetuks.ihub.entity.user.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
    @Email @NotBlank String email,
    @NotBlank String password,
    @NotBlank String name,
    String company,
    String position,
    String phoneNumber,
    String profileImageUrl,
    @NotNull UserStatus status
) {

  public UserCreateRequest applyEncodedPassword(String encodedPassword) {
    return new  UserCreateRequest(
        email,
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

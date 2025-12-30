package com.onetuks.ihub.controller.user;

import static com.onetuks.ihub.config.RoleDataInitializer.USER_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserResponse;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.UserMapper;
import com.onetuks.ihub.service.user.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestControllerImpl implements UserRestController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<UserResponse> createUser(
      @Valid @RequestBody UserCreateRequest request
  ) {
    User result = userService.create(
        request.applyEncodedPassword(passwordEncoder.encode(request.password())));
    UserResponse response = UserMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/users/" + response.userId()))
        .body(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<UserResponse> getUser(
      @PathVariable String userId
  ) {
    User result = userService.getById(userId);
    UserResponse response = UserMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Page<UserResponse>> getUsers(
      @PageableDefault Pageable pageable
  ) {
    Page<User> results = userService.getAll(pageable);
    Page<UserResponse> responses = results.map(UserMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable String userId,
      @Valid @RequestBody UserUpdateRequest request
  ) {
    User result = userService.update(userId,
        request.applyEncodedPassword(passwordEncoder.encode(request.password())));
    UserResponse response = UserMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<Void> deleteUser(
      @PathVariable String userId
  ) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }
}

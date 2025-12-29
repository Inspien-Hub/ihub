package com.onetuks.ihub.controller.user;

import static com.onetuks.ihub.config.RoleDataInitializer.USER_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserResponse;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestControllerImpl implements UserRestController {

  private final UserService userService;

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
    UserResponse response = UserMapper.toResponse(userService.create(request));
    return ResponseEntity.created(URI.create("/api/users/" + response.userId())).body(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<UserResponse> getUser(@PathVariable(name = "user-id") String userId) {
    return ResponseEntity.ok(UserMapper.toResponse(userService.getById(userId)));
  }

  @Override
  public ResponseEntity<Page<UserResponse>> getUsers(@PageableDefault Pageable pageable) {
    Page<UserResponse> response = userService.getAll(
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
        .map(UserMapper::toResponse);
    return ResponseEntity.ok(response);
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable(name = "user-id") String userId,
      @Valid @RequestBody UserUpdateRequest request) {
    return ResponseEntity.ok(UserMapper.toResponse(userService.update(userId, request)));
  }

  @RequiresRole(USER_FULL_ACCESS)
  @Override
  public ResponseEntity<Void> deleteUser(@PathVariable(name = "user-id") String userId) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }
}

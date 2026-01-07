package com.onetuks.ihub.controller.system;

import static com.onetuks.ihub.config.RoleDataInitializer.SYSTEM_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.system.SystemOwnerCreateRequest;
import com.onetuks.ihub.dto.system.SystemOwnerResponse;
import com.onetuks.ihub.entity.system.SystemOwner;
import com.onetuks.ihub.mapper.SystemOwnerMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.system.SystemOwnerService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemOwnerRestControllerImpl implements SystemOwnerRestController {

  private final CurrentUserProvider currentUserProvider;
  private final SystemOwnerService systemOwnerService;

  @Override
  public ResponseEntity<Page<SystemOwnerResponse>> getSystemOwners(
      @PathVariable String projectId,
      @RequestParam(required = false) String userId,
      @PageableDefault Pageable pageable) {
    Page<SystemOwner> results = systemOwnerService.getAll(
        currentUserProvider.resolveUser(), projectId, userId, pageable);
    return ResponseEntity.ok(results.map(SystemOwnerMapper::toResponse));
  }

  @RequiresRole({SYSTEM_FULL_ACCESS})
  @Override
  public ResponseEntity<SystemOwnerResponse> createSystemOwner(
      @PathVariable String projectId,
      @Valid @RequestBody SystemOwnerCreateRequest request) {
    SystemOwner result = systemOwnerService.create(
        currentUserProvider.resolveUser(), projectId, request);
    SystemOwnerResponse response = SystemOwnerMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/system-owners/" + response.systemOwnerId()))
        .body(response);
  }

  @RequiresRole({SYSTEM_FULL_ACCESS})
  @Override
  public ResponseEntity<Void> deleteSystemOwner(@PathVariable String systemOwnerId) {
    systemOwnerService.delete(currentUserProvider.resolveUser(), systemOwnerId);
    return ResponseEntity.noContent().build();
  }
}

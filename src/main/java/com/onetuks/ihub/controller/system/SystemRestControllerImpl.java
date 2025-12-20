package com.onetuks.ihub.controller.system;

import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemResponse;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import com.onetuks.ihub.service.system.SystemService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemRestControllerImpl implements SystemRestController {

  private final SystemService systemService;

  @Override
  public ResponseEntity<SystemResponse> createSystem(
      @Valid @RequestBody SystemCreateRequest request) {
    SystemResponse response = systemService.create(request);
    return ResponseEntity.created(URI.create("/api/systems/" + response.systemId()))
        .body(response);
  }

  @Override
  public ResponseEntity<SystemResponse> getSystem(@PathVariable Long systemId) {
    return ResponseEntity.ok(systemService.getById(systemId));
  }

  @Override
  public ResponseEntity<List<SystemResponse>> getSystems() {
    return ResponseEntity.ok(systemService.getAll());
  }

  @Override
  public ResponseEntity<SystemResponse> updateSystem(
      @PathVariable Long systemId, @Valid @RequestBody SystemUpdateRequest request) {
    return ResponseEntity.ok(systemService.update(systemId, request));
  }

  @Override
  public ResponseEntity<Void> deleteSystem(@PathVariable Long systemId) {
    systemService.delete(systemId);
    return ResponseEntity.noContent().build();
  }
}

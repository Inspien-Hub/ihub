package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceUpdateRequest;
import com.onetuks.ihub.service.interfaces.InterfaceService;
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
public class InterfaceRestControllerImpl implements InterfaceRestController {

  private final InterfaceService interfaceService;

  @Override
  public ResponseEntity<InterfaceResponse> createInterface(
      @Valid @RequestBody InterfaceCreateRequest request) {
    InterfaceResponse response = interfaceService.create(request);
    return ResponseEntity.created(URI.create("/api/interfaces/" + response.interfaceId()))
        .body(response);
  }

  @Override
  public ResponseEntity<InterfaceResponse> getInterface(@PathVariable Long interfaceId) {
    return ResponseEntity.ok(interfaceService.getById(interfaceId));
  }

  @Override
  public ResponseEntity<List<InterfaceResponse>> getInterfaces() {
    return ResponseEntity.ok(interfaceService.getAll());
  }

  @Override
  public ResponseEntity<InterfaceResponse> updateInterface(
      @PathVariable Long interfaceId, @Valid @RequestBody InterfaceUpdateRequest request) {
    return ResponseEntity.ok(interfaceService.update(interfaceId, request));
  }

  @Override
  public ResponseEntity<Void> deleteInterface(@PathVariable Long interfaceId) {
    interfaceService.delete(interfaceId);
    return ResponseEntity.noContent().build();
  }
}

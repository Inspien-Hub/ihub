package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.service.interfaces.InterfaceStatusService;
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
public class InterfaceStatusRestControllerImpl implements InterfaceStatusRestController {

  private final InterfaceStatusService interfaceStatusService;

  @Override
  public ResponseEntity<InterfaceStatusResponse> createInterfaceStatus(
      @Valid @RequestBody InterfaceStatusCreateRequest request) {
    InterfaceStatusResponse response = interfaceStatusService.create(request);
    return ResponseEntity.created(URI.create("/api/interface-statuses/" + response.statusId()))
        .body(response);
  }

  @Override
  public ResponseEntity<InterfaceStatusResponse> getInterfaceStatus(@PathVariable Long statusId) {
    return ResponseEntity.ok(interfaceStatusService.getById(statusId));
  }

  @Override
  public ResponseEntity<List<InterfaceStatusResponse>> getInterfaceStatuses() {
    return ResponseEntity.ok(interfaceStatusService.getAll());
  }

  @Override
  public ResponseEntity<InterfaceStatusResponse> updateInterfaceStatus(
      @PathVariable Long statusId, @Valid @RequestBody InterfaceStatusUpdateRequest request) {
    return ResponseEntity.ok(interfaceStatusService.update(statusId, request));
  }

  @Override
  public ResponseEntity<Void> deleteInterfaceStatus(@PathVariable Long statusId) {
    interfaceStatusService.delete(statusId);
    return ResponseEntity.noContent().build();
  }
}

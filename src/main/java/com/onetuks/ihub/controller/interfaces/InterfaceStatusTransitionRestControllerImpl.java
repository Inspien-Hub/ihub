package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionUpdateRequest;
import com.onetuks.ihub.service.interfaces.InterfaceStatusTransitionService;
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
public class InterfaceStatusTransitionRestControllerImpl
    implements InterfaceStatusTransitionRestController {

  private final InterfaceStatusTransitionService interfaceStatusTransitionService;

  @Override
  public ResponseEntity<InterfaceStatusTransitionResponse> createInterfaceStatusTransition(
      @Valid @RequestBody InterfaceStatusTransitionCreateRequest request) {
    InterfaceStatusTransitionResponse response = interfaceStatusTransitionService.create(request);
    return ResponseEntity.created(
            URI.create("/api/interface-status-transitions/" + response.transitionId()))
        .body(response);
  }

  @Override
  public ResponseEntity<InterfaceStatusTransitionResponse> getInterfaceStatusTransition(
      Long transitionId) {
    return ResponseEntity.ok(interfaceStatusTransitionService.getById(transitionId));
  }

  @Override
  public ResponseEntity<List<InterfaceStatusTransitionResponse>> getInterfaceStatusTransitions() {
    return ResponseEntity.ok(interfaceStatusTransitionService.getAll());
  }

  @Override
  public ResponseEntity<InterfaceStatusTransitionResponse> updateInterfaceStatusTransition(
      Long transitionId, @Valid @RequestBody InterfaceStatusTransitionUpdateRequest request) {
    return ResponseEntity.ok(interfaceStatusTransitionService.update(transitionId, request));
  }

  @Override
  public ResponseEntity<Void> deleteInterfaceStatusTransition(Long transitionId) {
    interfaceStatusTransitionService.delete(transitionId);
    return ResponseEntity.noContent().build();
  }
}

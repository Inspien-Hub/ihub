package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransition;
import com.onetuks.ihub.mapper.InterfaceStatusTransitionMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.interfaces.InterfaceStatusTransitionService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterfaceStatusTransitionRestControllerImpl
    implements InterfaceStatusTransitionRestController {

  private final CurrentUserProvider currentUserProvider;
  private final InterfaceStatusTransitionService interfaceStatusTransitionService;

  @Override
  public ResponseEntity<InterfaceStatusTransitionResponse> createInterfaceStatusTransition(
      @Valid @RequestBody InterfaceStatusTransitionCreateRequest request) {
    InterfaceStatusTransition result =
        interfaceStatusTransitionService.create(currentUserProvider.resolveUser(), request);
    InterfaceStatusTransitionResponse response = InterfaceStatusTransitionMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/interface-status-transitions/" + response.transitionId()))
        .body(response);
  }

  @Override
  public ResponseEntity<Page<InterfaceStatusTransitionResponse>> getInterfaceStatusTransitions(
      @PageableDefault Pageable pageable
  ) {
    Page<InterfaceStatusTransition> results =
        interfaceStatusTransitionService.getAll(pageable);
    Page<InterfaceStatusTransitionResponse> responses =
        results.map(InterfaceStatusTransitionMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<InterfaceStatusTransitionResponse> updateInterfaceStatusTransition(
      @PathVariable String transitionId,
      @Valid @RequestBody InterfaceStatusTransitionUpdateRequest request) {
    return ResponseEntity.ok(InterfaceStatusTransitionMapper.toResponse(
        interfaceStatusTransitionService.update(currentUserProvider.resolveUser(), transitionId,
            request)));
  }
}

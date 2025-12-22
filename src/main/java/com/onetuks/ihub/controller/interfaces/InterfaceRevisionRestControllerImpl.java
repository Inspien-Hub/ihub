package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceRevisionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceRevisionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceRevisionUpdateRequest;
import com.onetuks.ihub.mapper.InterfaceRevisionMapper;
import com.onetuks.ihub.service.interfaces.InterfaceRevisionService;
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
public class InterfaceRevisionRestControllerImpl implements InterfaceRevisionRestController {

  private final InterfaceRevisionService interfaceRevisionService;

  @Override
  public ResponseEntity<InterfaceRevisionResponse> createInterfaceRevision(
      @Valid @RequestBody InterfaceRevisionCreateRequest request) {
    InterfaceRevisionResponse response =
        InterfaceRevisionMapper.toResponse(interfaceRevisionService.create(request));
    return ResponseEntity.created(URI.create("/api/interface-revisions/" + response.revisionId()))
        .body(response);
  }

  @Override
  public ResponseEntity<InterfaceRevisionResponse> getInterfaceRevision(Long revisionId) {
    return ResponseEntity.ok(
        InterfaceRevisionMapper.toResponse(interfaceRevisionService.getById(revisionId)));
  }

  @Override
  public ResponseEntity<List<InterfaceRevisionResponse>> getInterfaceRevisions() {
    return ResponseEntity.ok(
        interfaceRevisionService.getAll().stream()
            .map(InterfaceRevisionMapper::toResponse)
            .toList());
  }

  @Override
  public ResponseEntity<InterfaceRevisionResponse> updateInterfaceRevision(
      Long revisionId, @Valid @RequestBody InterfaceRevisionUpdateRequest request) {
    return ResponseEntity.ok(
        InterfaceRevisionMapper.toResponse(interfaceRevisionService.update(revisionId, request)));
  }

  @Override
  public ResponseEntity<Void> deleteInterfaceRevision(Long revisionId) {
    interfaceRevisionService.delete(revisionId);
    return ResponseEntity.noContent().build();
  }
}

package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryUpdateRequest;
import com.onetuks.ihub.mapper.InterfaceStatusHistoryMapper;
import com.onetuks.ihub.service.interfaces.InterfaceStatusHistoryService;
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
public class InterfaceStatusHistoryRestControllerImpl
    implements InterfaceStatusHistoryRestController {

  private final InterfaceStatusHistoryService interfaceStatusHistoryService;

  @Override
  public ResponseEntity<InterfaceStatusHistoryResponse> createInterfaceStatusHistory(
      @Valid @RequestBody InterfaceStatusHistoryCreateRequest request) {
    InterfaceStatusHistoryResponse response =
        InterfaceStatusHistoryMapper.toResponse(interfaceStatusHistoryService.create(request));
    return ResponseEntity.created(
            URI.create("/api/interface-status-histories/" + response.historyId()))
        .body(response);
  }

  @Override
  public ResponseEntity<InterfaceStatusHistoryResponse> getInterfaceStatusHistory(
      Long historyId) {
    return ResponseEntity.ok(
        InterfaceStatusHistoryMapper.toResponse(interfaceStatusHistoryService.getById(historyId)));
  }

  @Override
  public ResponseEntity<List<InterfaceStatusHistoryResponse>> getInterfaceStatusHistories() {
    return ResponseEntity.ok(
        interfaceStatusHistoryService.getAll().stream()
            .map(InterfaceStatusHistoryMapper::toResponse)
            .toList());
  }

  @Override
  public ResponseEntity<InterfaceStatusHistoryResponse> updateInterfaceStatusHistory(
      Long historyId, @Valid @RequestBody InterfaceStatusHistoryUpdateRequest request) {
    return ResponseEntity.ok(InterfaceStatusHistoryMapper.toResponse(
        interfaceStatusHistoryService.update(historyId, request)));
  }

  @Override
  public ResponseEntity<Void> deleteInterfaceStatusHistory(Long historyId) {
    interfaceStatusHistoryService.delete(historyId);
    return ResponseEntity.noContent().build();
  }
}

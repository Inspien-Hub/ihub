package com.onetuks.ihub.controller.system;

import com.onetuks.ihub.dto.system.ConnectionCreateRequest;
import com.onetuks.ihub.dto.system.ConnectionResponse;
import com.onetuks.ihub.dto.system.ConnectionUpdateRequest;
import com.onetuks.ihub.entity.system.Connection;
import com.onetuks.ihub.mapper.ConnectionMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.system.ConnectionService;
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
public class ConnectionRestControllerImpl implements ConnectionRestController {

  private final CurrentUserProvider currentUserProvider;
  private final ConnectionService connectionService;

  @Override
  public ResponseEntity<Page<ConnectionResponse>> getConnections(
      @PathVariable String systemId, @PageableDefault Pageable pageable) {
    Page<Connection> results = connectionService.getAll(
        currentUserProvider.resolveUser(), systemId, pageable);
    Page<ConnectionResponse> responses = results.map(ConnectionMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<ConnectionResponse> createConnection(
      @PathVariable String systemId,
      @Valid @RequestBody ConnectionCreateRequest request) {
    Connection result = connectionService.create(
        currentUserProvider.resolveUser(), systemId, request);
    ConnectionResponse response = ConnectionMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/connections/" + response.connectionId()))
        .body(response);
  }

  @Override
  public ResponseEntity<ConnectionResponse> getConnection(@PathVariable String connectionId) {
    Connection result = connectionService.getById(currentUserProvider.resolveUser(), connectionId);
    ConnectionResponse response = ConnectionMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<ConnectionResponse> updateConnection(
      @PathVariable String connectionId, @Valid @RequestBody ConnectionUpdateRequest request) {
    Connection result = connectionService.update(currentUserProvider.resolveUser(), connectionId,
        request);
    ConnectionResponse response = ConnectionMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteConnection(@PathVariable String connectionId) {
    connectionService.delete(currentUserProvider.resolveUser(), connectionId);
    return ResponseEntity.noContent().build();
  }
}

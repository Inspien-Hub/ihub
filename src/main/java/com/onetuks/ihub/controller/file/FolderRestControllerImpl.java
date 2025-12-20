package com.onetuks.ihub.controller.file;

import com.onetuks.ihub.dto.file.FolderCreateRequest;
import com.onetuks.ihub.dto.file.FolderResponse;
import com.onetuks.ihub.dto.file.FolderUpdateRequest;
import com.onetuks.ihub.service.file.FolderService;
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
public class FolderRestControllerImpl implements FolderRestController {

  private final FolderService folderService;

  @Override
  public ResponseEntity<FolderResponse> createFolder(
      @Valid @RequestBody FolderCreateRequest request) {
    FolderResponse response = folderService.create(request);
    return ResponseEntity.created(URI.create("/api/folders/" + response.folderId()))
        .body(response);
  }

  @Override
  public ResponseEntity<FolderResponse> getFolder(@PathVariable Long folderId) {
    return ResponseEntity.ok(folderService.getById(folderId));
  }

  @Override
  public ResponseEntity<List<FolderResponse>> getFolders() {
    return ResponseEntity.ok(folderService.getAll());
  }

  @Override
  public ResponseEntity<FolderResponse> updateFolder(
      @PathVariable Long folderId, @Valid @RequestBody FolderUpdateRequest request) {
    return ResponseEntity.ok(folderService.update(folderId, request));
  }

  @Override
  public ResponseEntity<Void> deleteFolder(@PathVariable Long folderId) {
    folderService.delete(folderId);
    return ResponseEntity.noContent().build();
  }
}

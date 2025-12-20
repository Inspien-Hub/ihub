package com.onetuks.ihub.controller.file;

import com.onetuks.ihub.dto.file.FileCreateRequest;
import com.onetuks.ihub.dto.file.FileResponse;
import com.onetuks.ihub.dto.file.FileUpdateRequest;
import com.onetuks.ihub.service.file.FileService;
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
public class FileRestControllerImpl implements FileRestController {

  private final FileService fileService;

  @Override
  public ResponseEntity<FileResponse> createFile(@Valid @RequestBody FileCreateRequest request) {
    FileResponse response = fileService.create(request);
    return ResponseEntity.created(URI.create("/api/files/" + response.fileId()))
        .body(response);
  }

  @Override
  public ResponseEntity<FileResponse> getFile(@PathVariable Long fileId) {
    return ResponseEntity.ok(fileService.getById(fileId));
  }

  @Override
  public ResponseEntity<List<FileResponse>> getFiles() {
    return ResponseEntity.ok(fileService.getAll());
  }

  @Override
  public ResponseEntity<FileResponse> updateFile(
      @PathVariable Long fileId, @Valid @RequestBody FileUpdateRequest request) {
    return ResponseEntity.ok(fileService.update(fileId, request));
  }

  @Override
  public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
    fileService.delete(fileId);
    return ResponseEntity.noContent().build();
  }
}

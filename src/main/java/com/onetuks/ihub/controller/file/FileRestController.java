package com.onetuks.ihub.controller.file;

import com.onetuks.ihub.dto.file.FileCreateRequest;
import com.onetuks.ihub.dto.file.FileResponse;
import com.onetuks.ihub.dto.file.FileUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/files")
@Tag(name = "File", description = "File management APIs")
public interface FileRestController {

  @Operation(summary = "Create file")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "File created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<FileResponse> createFile(@Valid @RequestBody FileCreateRequest request);

  @Operation(summary = "Get file by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "File found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "File not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{fileId}")
  ResponseEntity<FileResponse> getFile(@PathVariable Long fileId);

  @Operation(summary = "List files")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Files listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<FileResponse>> getFiles();

  @Operation(summary = "Update file")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "File updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "File not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{fileId}")
  ResponseEntity<FileResponse> updateFile(
      @PathVariable Long fileId,
      @Valid @RequestBody FileUpdateRequest request);

  @Operation(summary = "Delete file")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "File deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "File not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{fileId}")
  ResponseEntity<Void> deleteFile(@PathVariable Long fileId);
}

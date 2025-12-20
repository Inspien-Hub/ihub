package com.onetuks.ihub.controller.file;

import com.onetuks.ihub.dto.file.FolderCreateRequest;
import com.onetuks.ihub.dto.file.FolderResponse;
import com.onetuks.ihub.dto.file.FolderUpdateRequest;
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

@RequestMapping("/api/folders")
@Tag(name = "Folder", description = "Folder management APIs")
public interface FolderRestController {

  @Operation(summary = "Create folder")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Folder created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<FolderResponse> createFolder(@Valid @RequestBody FolderCreateRequest request);

  @Operation(summary = "Get folder by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Folder found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Folder not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{folderId}")
  ResponseEntity<FolderResponse> getFolder(@PathVariable Long folderId);

  @Operation(summary = "List folders")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Folders listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<FolderResponse>> getFolders();

  @Operation(summary = "Update folder")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Folder updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Folder not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{folderId}")
  ResponseEntity<FolderResponse> updateFolder(
      @PathVariable Long folderId,
      @Valid @RequestBody FolderUpdateRequest request);

  @Operation(summary = "Delete folder")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Folder deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Folder not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{folderId}")
  ResponseEntity<Void> deleteFolder(@PathVariable Long folderId);
}

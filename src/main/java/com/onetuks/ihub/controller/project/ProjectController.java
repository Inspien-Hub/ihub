package com.onetuks.ihub.controller.project;

import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectResponse;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/projects")
@Tag(name = "Project", description = "Project management APIs")
public interface ProjectController {

  @Operation(summary = "Create project")
  @PostMapping
  ResponseEntity<ProjectResponse> createProject(
      @Valid @RequestBody ProjectCreateRequest request);

  @Operation(summary = "Get project by id")
  @GetMapping("/{projectId}")
  ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId);

  @Operation(summary = "List projects")
  @GetMapping
  ResponseEntity<List<ProjectResponse>> getProjects();

  @Operation(summary = "Update project")
  @PutMapping("/{projectId}")
  ResponseEntity<ProjectResponse> updateProject(
      @PathVariable Long projectId,
      @Valid @RequestBody ProjectUpdateRequest request);

  @Operation(summary = "Delete project")
  @DeleteMapping("/{projectId}")
  ResponseEntity<Void> deleteProject(@PathVariable Long projectId);
}

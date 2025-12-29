package com.onetuks.ihub.controller.project;

import static com.onetuks.ihub.config.RoleDataInitializer.PROJECT_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectResponse;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.mapper.ProjectMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.project.ProjectService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectRestControllerImpl implements ProjectRestController {

  private final CurrentUserProvider currentUserProvider;
  private final ProjectService projectService;

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<ProjectResponse> createProject(
      @Valid @RequestBody ProjectCreateRequest request) {
    Project project = projectService.create(currentUserProvider.resolveUser(), request);
    ProjectResponse response = ProjectMapper.toResponse(project);
    return ResponseEntity.created(URI.create("/api/projects/" + response.projectId()))
        .body(response);
  }

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<ProjectResponse> getProject(@PathVariable String projectId) {
    Project project = projectService.getById(currentUserProvider.resolveUser(), projectId);
    return ResponseEntity.ok(ProjectMapper.toResponse(project));
  }

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<Page<ProjectResponse>> getProjects(@PageableDefault Pageable pageable) {
    Page<ProjectResponse> responses = projectService.getAll(
            currentUserProvider.resolveUser(),
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
        .map(ProjectMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<ProjectResponse> updateProject(
      @PathVariable String projectId,
      @Valid @RequestBody ProjectUpdateRequest request) {
    Project project = projectService.update(currentUserProvider.resolveUser(), projectId, request);
    return ResponseEntity.ok(ProjectMapper.toResponse(project));
  }

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<Void> deleteProject(@PathVariable String projectId) {
    projectService.delete(currentUserProvider.resolveUser(), projectId);
    return ResponseEntity.noContent().build();
  }
}

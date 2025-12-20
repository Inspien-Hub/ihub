package com.onetuks.ihub.controller.project;

import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import com.onetuks.ihub.service.project.ProjectMemberService;
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
public class ProjectMemberRestControllerImpl implements ProjectMemberRestController {

  private final ProjectMemberService projectMemberService;

  @Override
  public ResponseEntity<ProjectMemberResponse> createProjectMember(
      @Valid @RequestBody ProjectMemberCreateRequest request) {
    ProjectMemberResponse response = projectMemberService.create(request);
    return ResponseEntity.created(URI.create("/api/project-members/" + response.projectMemberId()))
        .body(response);
  }

  @Override
  public ResponseEntity<ProjectMemberResponse> getProjectMember(Long projectMemberId) {
    return ResponseEntity.ok(projectMemberService.getById(projectMemberId));
  }

  @Override
  public ResponseEntity<List<ProjectMemberResponse>> getProjectMembers() {
    return ResponseEntity.ok(projectMemberService.getAll());
  }

  @Override
  public ResponseEntity<ProjectMemberResponse> updateProjectMember(
      Long projectMemberId, @Valid @RequestBody ProjectMemberUpdateRequest request) {
    return ResponseEntity.ok(projectMemberService.update(projectMemberId, request));
  }

  @Override
  public ResponseEntity<Void> deleteProjectMember(Long projectMemberId) {
    projectMemberService.delete(projectMemberId);
    return ResponseEntity.noContent().build();
  }
}

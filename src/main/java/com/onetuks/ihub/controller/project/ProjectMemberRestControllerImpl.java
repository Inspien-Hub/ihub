package com.onetuks.ihub.controller.project;

import static com.onetuks.ihub.config.RoleDataInitializer.PROJECT_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.mapper.ProjectMemberMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.project.ProjectMemberService;
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
public class ProjectMemberRestControllerImpl implements ProjectMemberRestController {

  private final CurrentUserProvider currentUserProvider;
  private final ProjectMemberService projectMemberService;

  @Override
  public ResponseEntity<Page<ProjectMemberResponse>> getProjectMembers(
      @PathVariable String projectId,
      @PageableDefault Pageable pageable
  ) {
    Page<ProjectMember> results = projectMemberService.getAll(
        currentUserProvider.resolveUser(), projectId, pageable);
    Page<ProjectMemberResponse> response = results.map(ProjectMemberMapper::toResponse);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<ProjectMemberResponse> createProjectMember(
      @PathVariable String projectId,
      @Valid @RequestBody ProjectMemberCreateRequest request
  ) {
    ProjectMember result = projectMemberService.create(
        currentUserProvider.resolveUser(), projectId, request);
    ProjectMemberResponse response = ProjectMemberMapper.toResponse(result);
    return ResponseEntity.created(
            URI.create("/api/projects/" + projectId + "/members/" + response.projectMemberId()))
        .body(response);
  }

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<ProjectMemberResponse> updateProjectMember(
      @PathVariable String projectId,
      @PathVariable String memberId,
      @Valid @RequestBody ProjectMemberUpdateRequest request
  ) {
    ProjectMember result = projectMemberService.update(
        currentUserProvider.resolveUser(), projectId, memberId, request);
    ProjectMemberResponse response = ProjectMemberMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @RequiresRole({PROJECT_FULL_ACCESS})
  @Override
  public ResponseEntity<Void> deleteProjectMember(
      @PathVariable String projectId,
      @PathVariable String memberId
  ) {
    projectMemberService.delete(currentUserProvider.resolveUser(), projectId, memberId);
    return ResponseEntity.noContent().build();
  }
}

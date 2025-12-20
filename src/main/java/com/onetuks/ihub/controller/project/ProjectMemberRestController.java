package com.onetuks.ihub.controller.project;

import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
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

@RequestMapping("/api/project-members")
@Tag(name = "ProjectMember", description = "Project member management APIs")
public interface ProjectMemberRestController {

  @Operation(summary = "Create project member")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Project member created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<ProjectMemberResponse> createProjectMember(
      @Valid @RequestBody ProjectMemberCreateRequest request);

  @Operation(summary = "Get project member by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Project member found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Project member not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{projectMemberId}")
  ResponseEntity<ProjectMemberResponse> getProjectMember(@PathVariable Long projectMemberId);

  @Operation(summary = "List project members")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Project members listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<ProjectMemberResponse>> getProjectMembers();

  @Operation(summary = "Update project member")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Project member updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Project member not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{projectMemberId}")
  ResponseEntity<ProjectMemberResponse> updateProjectMember(
      @PathVariable Long projectMemberId,
      @Valid @RequestBody ProjectMemberUpdateRequest request);

  @Operation(summary = "Delete project member")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Project member deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Project member not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{projectMemberId}")
  ResponseEntity<Void> deleteProjectMember(@PathVariable Long projectMemberId);
}

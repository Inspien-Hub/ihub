package com.onetuks.ihub.controller.project;

import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Tag(name = "Project Member", description = "프로젝트 멤버 관리 API")
public interface ProjectMemberRestController {

  @Operation(summary = "프로젝트 멤버 목록 조회", description = "프로젝트 멤버 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "프로젝트 멤버 목록 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping("/api/projects/{projectId}/members")
  ResponseEntity<Page<ProjectMemberResponse>> getProjectMembers(
      @PathVariable String projectId,
      @PageableDefault Pageable pageable
  );

  @Operation(summary = "프로젝트 멤버 초대", description = "프로젝트 멤버를 초대합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "프로젝트 멤버 초대 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PostMapping("/api/projects/{projectId}/members")
  ResponseEntity<ProjectMemberResponse> createProjectMember(
      @PathVariable String projectId,
      @Valid @RequestBody ProjectMemberCreateRequest request
  );

  @Operation(summary = "프로젝트 멤버 수정", description = "프로젝트 멤버 정보를 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "프로젝트 멤버 수정 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PatchMapping("/api/projects/{projectId}/members/{memberId}")
  ResponseEntity<ProjectMemberResponse> updateProjectMember(
      @PathVariable String projectId,
      @PathVariable String memberId,
      @Valid @RequestBody ProjectMemberUpdateRequest request
  );

  @Operation(summary = "프로젝트 멤버 삭제", description = "프로젝트 멤버를 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "프로젝트 멤버 삭제 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @DeleteMapping("/api/projects/{projectId}/members/{memberId}")
  ResponseEntity<Void> deleteProjectMember(
      @PathVariable String projectId,
      @PathVariable String memberId
  );
}

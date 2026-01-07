package com.onetuks.ihub.service.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProjectMemberCheckComponent {

  private final ProjectMemberJpaRepository projectMemberRepository;

  @Transactional(readOnly = true)
  public void checkIsProjectViewer(String userId, String projectId) {
    boolean hasAuthority = projectMemberRepository.findByProject_ProjectIdAndUser_UserId(
        projectId, userId)
        .map(projectMember ->
            projectMember.getRole().hasMuchAuthorityThan(ProjectMemberRole.PROJECT_VIEWER))
        .orElseThrow(() -> new AccessDeniedException("프로젝트 참여자가 아닙니다."));
    if (!hasAuthority) {
      throw new AccessDeniedException("프로젝트 참여자가 아닙니다.");
    }
  }

  @Transactional(readOnly = true)
  public void checkIsProjectMember(String userId, String projectId) {
    boolean hasAuthority = projectMemberRepository.findByProject_ProjectIdAndUser_UserId(
        projectId, userId)
        .map(projectMember ->
            projectMember.getRole().hasMuchAuthorityThan(ProjectMemberRole.PROJECT_MEMBER))
        .orElseThrow(() -> new AccessDeniedException("프로젝트 멤버가 아닙니다."));
    if (!hasAuthority) {
      throw new AccessDeniedException("프로젝트 멤버가 아닙니다.");
    }
  }

  @Transactional(readOnly = true)
  public void checkIsProjectOwner(String userId, String projectId) {
    boolean hasAuthority = projectMemberRepository.findByProject_ProjectIdAndUser_UserId(
        projectId, userId)
        .map(projectMember ->
            projectMember.getRole().hasMuchAuthorityThan(ProjectMemberRole.PROJECT_OWNER))
        .orElseThrow(() -> new AccessDeniedException("프로젝트 오너가 아닙니다."));
    if (!hasAuthority) {
      throw new AccessDeniedException("프로젝트 오너가 아닙니다.");
    }
  }
}

package com.onetuks.ihub.service.project;

import static com.onetuks.ihub.config.RoleDataInitializer.PROJECT_FULL_ACCESS;

import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.ProjectMemberMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.repository.UserRoleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final ProjectMemberJpaRepository projectMemberJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final UserRoleJpaRepository userRoleJpaRepository;

  @Transactional
  public ProjectMember create(
      User currentUser, String projectId, ProjectMemberCreateRequest request) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);
    return projectMemberJpaRepository.save(
        ProjectMemberMapper.applyCreate(findProject(projectId), findUser(request.userId())));
  }

  @Transactional(readOnly = true)
  public Page<ProjectMember> getAll(User currentUser, String projectId, Pageable pageable) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);
    return projectMemberJpaRepository.findAllByProject_ProjectIdAndUser_StatusNot(
        projectId, UserStatus.DELETED, pageable);
  }

  @Transactional
  public ProjectMember update(
      User currentUser, String projectId, String memberId, ProjectMemberUpdateRequest request) {
    projectMemberCheckComponent.checkIsProjectOwner(currentUser.getUserId(), projectId);
    ProjectMember projectMember = findMember(projectId, memberId);
    if (request.role() != null) {
      validateAdminAssignment(projectMember.getUser().getUserId(), request.role());
    }
    return ProjectMemberMapper.applyUpdate(projectMember, request);
  }

  @Transactional
  public void delete(User currentUser, String projectId, String memberId) {
    projectMemberCheckComponent.checkIsProjectOwner(currentUser.getUserId(), projectId);
    ProjectMember projectMember = findMember(projectId, memberId);
    projectMemberJpaRepository.delete(projectMember);
  }

  private ProjectMember findMember(String projectId, String memberId) {
    ProjectMember projectMember = projectMemberJpaRepository.findById(memberId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Project member not found: " + memberId));
    if (!projectId.equals(projectMember.getProject().getProjectId())) {
      throw new EntityNotFoundException("Project member not found: " + memberId);
    }
    return projectMember;
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }

  private void validateAdminAssignment(String userId, ProjectMemberRole role) {
    if (role != ProjectMemberRole.PROJECT_OWNER) {
      return;
    }
    boolean hasProjectFullAccess = userRoleJpaRepository.findAllByUser_UserId(userId).stream()
        .anyMatch(userRole -> PROJECT_FULL_ACCESS.equals(userRole.getRole().getRoleName()));
    if (!hasProjectFullAccess) {
      throw new AccessDeniedException("프로젝트 관리자 권한을 부여할 수 없습니다.");
    }
  }
}

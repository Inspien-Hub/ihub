package com.onetuks.ihub.service.project;

import com.onetuks.ihub.dto.project.ProjectCreateRequest;
import com.onetuks.ihub.dto.project.ProjectUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.mapper.ProjectMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectJpaRepository projectRepository;
  private final ProjectMemberJpaRepository projectMemberRepository;
  private final UserJpaRepository userRepository;

  @Transactional
  public Project create(ProjectCreateRequest request) {
    Project project = new Project();
    ProjectMapper.applyCreate(project, request);
    project.setCreatedBy(findUser(request.createdById()));
    project.setCurrentAdmin(findUser(request.currentAdminId()));
    return projectRepository.save(project);
  }

  @Transactional(readOnly = true)
  public Project getById(String projectId) {
    Project project = findEntity(projectId);
//    boolean isProjectMember = projectMemberRepository.existsByProjectAndUser(project, user);
//    if (!isProjectMember) {
//      throw new AccessDeniedException("프로젝트 멤버가 아닌 사람은 접근할 수 없습니다.");
//    }
    return project;
  }

  @Transactional(readOnly = true)
  public List<Project> getAll() {
    return projectRepository.findAll();
  }

  @Transactional
  public Project update(String projectId, ProjectUpdateRequest request) {
    Project project = findEntity(projectId);
    ProjectMapper.applyUpdate(project, request);
    if (request.currentAdminId() != null) {
      project.setCurrentAdmin(findUser(request.currentAdminId()));
    }
    return project;
  }

  @Transactional
  public void delete(String projectId) {
    Project project = findEntity(projectId);
    projectRepository.delete(project);
  }

  private Project findEntity(String projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(String userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

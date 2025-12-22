package com.onetuks.ihub.service.project;

import com.onetuks.ihub.dto.project.ProjectMemberCreateRequest;
import com.onetuks.ihub.dto.project.ProjectMemberUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.ProjectMemberMapper;
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
public class ProjectMemberService {

  private final ProjectMemberJpaRepository projectMemberJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public ProjectMember create(ProjectMemberCreateRequest request) {
    ProjectMember member = new ProjectMember();
    ProjectMemberMapper.applyCreate(member, request);
    member.setProject(findProject(request.projectId()));
    member.setUser(findUser(request.userId()));
    return projectMemberJpaRepository.save(member);
  }

  @Transactional(readOnly = true)
  public ProjectMember getById(Long projectMemberId) {
    return findEntity(projectMemberId);
  }

  @Transactional(readOnly = true)
  public List<ProjectMember> getAll() {
    return projectMemberJpaRepository.findAll();
  }

  @Transactional
  public ProjectMember update(Long projectMemberId, ProjectMemberUpdateRequest request) {
    ProjectMember member = findEntity(projectMemberId);
    ProjectMemberMapper.applyUpdate(member, request);
    return member;
  }

  @Transactional
  public void delete(Long projectMemberId) {
    ProjectMember member = findEntity(projectMemberId);
    projectMemberJpaRepository.delete(member);
  }

  private ProjectMember findEntity(Long projectMemberId) {
    return projectMemberJpaRepository.findById(projectMemberId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Project member not found: " + projectMemberId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

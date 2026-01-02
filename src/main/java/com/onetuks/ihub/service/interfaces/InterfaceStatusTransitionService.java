package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusTransitionUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransition;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.InterfaceStatusTransitionMapper;
import com.onetuks.ihub.repository.InterfaceStatusJpaRepository;
import com.onetuks.ihub.repository.InterfaceStatusTransitionJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceStatusTransitionService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final InterfaceStatusTransitionJpaRepository interfaceStatusTransitionJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final InterfaceStatusJpaRepository interfaceStatusJpaRepository;

  @Transactional
  public InterfaceStatusTransition create(
      User currentUser, InterfaceStatusTransitionCreateRequest request) {
    projectMemberCheckComponent.checkIsProjectMember(currentUser, request.projectId());
    return interfaceStatusTransitionJpaRepository.save(
        InterfaceStatusTransitionMapper.applyCreate(
            findProject(request.projectId()),
            findStatus(request.fromStatusId()),
            findStatus(request.toStatusId()),
            currentUser,
            request)
    );
  }

  @Transactional(readOnly = true)
  public Page<InterfaceStatusTransition> getAll(Pageable pageable) {
    return interfaceStatusTransitionJpaRepository.findAll(pageable);
  }

  @Transactional
  public InterfaceStatusTransition update(
      User currentUser, String transitionId, InterfaceStatusTransitionUpdateRequest request) {
    InterfaceStatusTransition transition = findEntity(transitionId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        transition.getProject().getProjectId());
    return InterfaceStatusTransitionMapper.applyUpdate(
        transition,
        findStatus(request.fromStatusId()),
        findStatus(request.toStatusId()),
        request);
  }

  private InterfaceStatusTransition findEntity(String transitionId) {
    return interfaceStatusTransitionJpaRepository.findById(transitionId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Interface status transition not found: " + transitionId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private InterfaceStatus findStatus(String statusId) {
    return interfaceStatusJpaRepository.findById(statusId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Interface status not found: " + statusId));
  }
}

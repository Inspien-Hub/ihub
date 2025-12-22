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
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceStatusTransitionService {

  private final InterfaceStatusTransitionJpaRepository interfaceStatusTransitionJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final InterfaceStatusJpaRepository interfaceStatusJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public InterfaceStatusTransition create(
      InterfaceStatusTransitionCreateRequest request) {
    InterfaceStatusTransition transition = new InterfaceStatusTransition();
    InterfaceStatusTransitionMapper.applyCreate(transition, request);
    transition.setProject(findProject(request.projectId()));
    transition.setFromStatus(findStatus(request.fromStatusId()));
    transition.setToStatus(findStatus(request.toStatusId()));
    transition.setCreatedBy(findUser(request.createdById()));
    return interfaceStatusTransitionJpaRepository.save(transition);
  }

  @Transactional(readOnly = true)
  public InterfaceStatusTransition getById(Long transitionId) {
    return findEntity(transitionId);
  }

  @Transactional(readOnly = true)
  public List<InterfaceStatusTransition> getAll() {
    return interfaceStatusTransitionJpaRepository.findAll();
  }

  @Transactional
  public InterfaceStatusTransition update(
      Long transitionId, InterfaceStatusTransitionUpdateRequest request) {
    InterfaceStatusTransition transition = findEntity(transitionId);
    InterfaceStatusTransitionMapper.applyUpdate(transition, request);
    if (request.fromStatusId() != null) {
      transition.setFromStatus(findStatus(request.fromStatusId()));
    }
    if (request.toStatusId() != null) {
      transition.setToStatus(findStatus(request.toStatusId()));
    }
    return transition;
  }

  @Transactional
  public void delete(Long transitionId) {
    InterfaceStatusTransition transition = findEntity(transitionId);
    interfaceStatusTransitionJpaRepository.delete(transition);
  }

  private InterfaceStatusTransition findEntity(Long transitionId) {
    return interfaceStatusTransitionJpaRepository.findById(transitionId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Interface status transition not found: " + transitionId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private InterfaceStatus findStatus(Long statusId) {
    return interfaceStatusJpaRepository.findById(statusId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Interface status not found: " + statusId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceUpdateRequest;
import com.onetuks.ihub.entity.interfaces.Interface;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.InterfaceMapper;
import com.onetuks.ihub.repository.InterfaceJpaRepository;
import com.onetuks.ihub.repository.InterfaceStatusJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceService {

  private final InterfaceJpaRepository interfaceJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final SystemJpaRepository systemJpaRepository;
  private final InterfaceStatusJpaRepository interfaceStatusJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Interface create(InterfaceCreateRequest request) {
    Interface anInterface = new Interface();
    InterfaceMapper.applyCreate(anInterface, request);
    anInterface.setProject(findProject(request.projectId()));
    anInterface.setSourceSystem(findSystem(request.sourceSystemId()));
    anInterface.setTargetSystem(findSystem(request.targetSystemId()));
    anInterface.setStatus(findStatus(request.statusId()));
    anInterface.setCreatedBy(findUser(request.createdById()));
    return interfaceJpaRepository.save(anInterface);
  }

  @Transactional(readOnly = true)
  public Interface getById(Long interfaceId) {
    return findEntity(interfaceId);
  }

  @Transactional(readOnly = true)
  public List<Interface> getAll() {
    return interfaceJpaRepository.findAll();
  }

  @Transactional
  public Interface update(Long interfaceId, InterfaceUpdateRequest request) {
    Interface anInterface = findEntity(interfaceId);
    InterfaceMapper.applyUpdate(anInterface, request);
    if (request.sourceSystemId() != null) {
      anInterface.setSourceSystem(findSystem(request.sourceSystemId()));
    }
    if (request.targetSystemId() != null) {
      anInterface.setTargetSystem(findSystem(request.targetSystemId()));
    }
    if (request.statusId() != null) {
      anInterface.setStatus(findStatus(request.statusId()));
    }
    return anInterface;
  }

  @Transactional
  public void delete(Long interfaceId) {
    Interface anInterface = findEntity(interfaceId);
    interfaceJpaRepository.delete(anInterface);
  }

  private Interface findEntity(Long interfaceId) {
    return interfaceJpaRepository.findById(interfaceId)
        .orElseThrow(() -> new EntityNotFoundException("Interface not found: " + interfaceId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private System findSystem(Long systemId) {
    return systemJpaRepository.findById(systemId)
        .orElseThrow(() -> new EntityNotFoundException("System not found: " + systemId));
  }

  private InterfaceStatus findStatus(Long statusId) {
    return interfaceStatusJpaRepository.findById(statusId)
        .orElseThrow(() -> new EntityNotFoundException("Interface status not found: " + statusId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

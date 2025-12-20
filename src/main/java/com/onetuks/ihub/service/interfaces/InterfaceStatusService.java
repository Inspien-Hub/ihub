package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.mapper.InterfaceStatusMapper;
import com.onetuks.ihub.repository.InterfaceStatusJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceStatusService {

  private final InterfaceStatusJpaRepository interfaceStatusJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public InterfaceStatusResponse create(InterfaceStatusCreateRequest request) {
    InterfaceStatus status = new InterfaceStatus();
    InterfaceStatusMapper.applyCreate(status, request);
    status.setProject(findProject(request.projectId()));
    InterfaceStatus saved = interfaceStatusJpaRepository.save(status);
    return InterfaceStatusMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public InterfaceStatusResponse getById(Long statusId) {
    return InterfaceStatusMapper.toResponse(findEntity(statusId));
  }

  @Transactional(readOnly = true)
  public List<InterfaceStatusResponse> getAll() {
    return interfaceStatusJpaRepository.findAll().stream()
        .map(InterfaceStatusMapper::toResponse)
        .toList();
  }

  @Transactional
  public InterfaceStatusResponse update(Long statusId, InterfaceStatusUpdateRequest request) {
    InterfaceStatus status = findEntity(statusId);
    InterfaceStatusMapper.applyUpdate(status, request);
    return InterfaceStatusMapper.toResponse(status);
  }

  @Transactional
  public void delete(Long statusId) {
    InterfaceStatus status = findEntity(statusId);
    interfaceStatusJpaRepository.delete(status);
  }

  private InterfaceStatus findEntity(Long statusId) {
    return interfaceStatusJpaRepository.findById(statusId)
        .orElseThrow(() -> new EntityNotFoundException("Interface status not found: " + statusId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

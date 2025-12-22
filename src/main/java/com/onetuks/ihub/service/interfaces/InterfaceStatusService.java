package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
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
  public InterfaceStatus create(InterfaceStatusCreateRequest request) {
    InterfaceStatus status = new InterfaceStatus();
    InterfaceStatusMapper.applyCreate(status, request);
    status.setProject(findProject(request.projectId()));
    return interfaceStatusJpaRepository.save(status);
  }

  @Transactional(readOnly = true)
  public InterfaceStatus getById(Long statusId) {
    return findEntity(statusId);
  }

  @Transactional(readOnly = true)
  public List<InterfaceStatus> getAll() {
    return interfaceStatusJpaRepository.findAll();
  }

  @Transactional
  public InterfaceStatus update(Long statusId, InterfaceStatusUpdateRequest request) {
    InterfaceStatus status = findEntity(statusId);
    InterfaceStatusMapper.applyUpdate(status, request);
    return status;
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

package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceRevisionCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceRevisionResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceRevisionUpdateRequest;
import com.onetuks.ihub.entity.interfaces.Interface;
import com.onetuks.ihub.entity.interfaces.InterfaceRevision;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.InterfaceRevisionMapper;
import com.onetuks.ihub.repository.InterfaceJpaRepository;
import com.onetuks.ihub.repository.InterfaceRevisionJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceRevisionService {

  private final InterfaceRevisionJpaRepository interfaceRevisionJpaRepository;
  private final InterfaceJpaRepository interfaceJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public InterfaceRevisionResponse create(InterfaceRevisionCreateRequest request) {
    InterfaceRevision revision = new InterfaceRevision();
    InterfaceRevisionMapper.applyCreate(revision, request);
    revision.setAnInterface(findInterface(request.interfaceId()));
    revision.setChangedBy(findUser(request.changedById()));
    InterfaceRevision saved = interfaceRevisionJpaRepository.save(revision);
    return InterfaceRevisionMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public InterfaceRevisionResponse getById(Long revisionId) {
    return InterfaceRevisionMapper.toResponse(findEntity(revisionId));
  }

  @Transactional(readOnly = true)
  public List<InterfaceRevisionResponse> getAll() {
    return interfaceRevisionJpaRepository.findAll().stream()
        .map(InterfaceRevisionMapper::toResponse)
        .toList();
  }

  @Transactional
  public InterfaceRevisionResponse update(Long revisionId, InterfaceRevisionUpdateRequest request) {
    InterfaceRevision revision = findEntity(revisionId);
    InterfaceRevisionMapper.applyUpdate(revision, request);
    if (request.changedById() != null) {
      revision.setChangedBy(findUser(request.changedById()));
    }
    return InterfaceRevisionMapper.toResponse(revision);
  }

  @Transactional
  public void delete(Long revisionId) {
    InterfaceRevision revision = findEntity(revisionId);
    interfaceRevisionJpaRepository.delete(revision);
  }

  private InterfaceRevision findEntity(Long revisionId) {
    return interfaceRevisionJpaRepository.findById(revisionId)
        .orElseThrow(() -> new EntityNotFoundException("Interface revision not found: " + revisionId));
  }

  private Interface findInterface(Long interfaceId) {
    return interfaceJpaRepository.findById(interfaceId)
        .orElseThrow(() -> new EntityNotFoundException("Interface not found: " + interfaceId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

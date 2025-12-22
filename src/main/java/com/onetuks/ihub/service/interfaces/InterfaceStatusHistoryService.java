package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryUpdateRequest;
import com.onetuks.ihub.entity.interfaces.Interface;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusHistory;
import com.onetuks.ihub.entity.task.Task;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.InterfaceStatusHistoryMapper;
import com.onetuks.ihub.repository.InterfaceJpaRepository;
import com.onetuks.ihub.repository.InterfaceStatusHistoryJpaRepository;
import com.onetuks.ihub.repository.InterfaceStatusJpaRepository;
import com.onetuks.ihub.repository.TaskJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceStatusHistoryService {

  private final InterfaceStatusHistoryJpaRepository interfaceStatusHistoryJpaRepository;
  private final InterfaceJpaRepository interfaceJpaRepository;
  private final InterfaceStatusJpaRepository interfaceStatusJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final TaskJpaRepository taskJpaRepository;

  @Transactional
  public InterfaceStatusHistory create(InterfaceStatusHistoryCreateRequest request) {
    InterfaceStatusHistory history = new InterfaceStatusHistory();
    InterfaceStatusHistoryMapper.applyCreate(history, request);
    history.setAnInterface(findInterface(request.interfaceId()));
    history.setFromStatus(findStatus(request.fromStatusId()));
    history.setToStatus(findStatus(request.toStatusId()));
    history.setChangedBy(findUser(request.changedById()));
    history.setRelatedTask(findTask(request.relatedTaskId()));
    return interfaceStatusHistoryJpaRepository.save(history);
  }

  @Transactional(readOnly = true)
  public InterfaceStatusHistory getById(Long historyId) {
    return findEntity(historyId);
  }

  @Transactional(readOnly = true)
  public List<InterfaceStatusHistory> getAll() {
    return interfaceStatusHistoryJpaRepository.findAll();
  }

  @Transactional
  public InterfaceStatusHistory update(
      Long historyId, InterfaceStatusHistoryUpdateRequest request) {
    InterfaceStatusHistory history = findEntity(historyId);
    InterfaceStatusHistoryMapper.applyUpdate(history, request);
    if (request.toStatusId() != null) {
      history.setToStatus(findStatus(request.toStatusId()));
    }
    return history;
  }

  @Transactional
  public void delete(Long historyId) {
    InterfaceStatusHistory history = findEntity(historyId);
    interfaceStatusHistoryJpaRepository.delete(history);
  }

  private InterfaceStatusHistory findEntity(Long historyId) {
    return interfaceStatusHistoryJpaRepository.findById(historyId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Interface status history not found: " + historyId));
  }

  private Interface findInterface(Long interfaceId) {
    return interfaceJpaRepository.findById(interfaceId)
        .orElseThrow(() -> new EntityNotFoundException("Interface not found: " + interfaceId));
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

  private Task findTask(Long taskId) {
    return taskJpaRepository.findById(taskId)
        .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));
  }
}

package com.onetuks.ihub.service.event;

import com.onetuks.ihub.dto.event.EventCreateRequest;
import com.onetuks.ihub.dto.event.EventResponse;
import com.onetuks.ihub.dto.event.EventUpdateRequest;
import com.onetuks.ihub.entity.event.Event;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.EventMapper;
import com.onetuks.ihub.repository.EventJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

  private final EventJpaRepository eventJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public EventResponse create(EventCreateRequest request) {
    Event event = new Event();
    EventMapper.applyCreate(event, request);
    event.setProject(findProject(request.projectId()));
    if (request.createdById() != null) {
      event.setCreatedBy(findUser(request.createdById()));
    }
    Event saved = eventJpaRepository.save(event);
    return EventMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public EventResponse getById(Long eventId) {
    return EventMapper.toResponse(findEntity(eventId));
  }

  @Transactional(readOnly = true)
  public List<EventResponse> getAll() {
    return eventJpaRepository.findAll().stream()
        .map(EventMapper::toResponse)
        .toList();
  }

  @Transactional
  public EventResponse update(Long eventId, EventUpdateRequest request) {
    Event event = findEntity(eventId);
    EventMapper.applyUpdate(event, request);
    return EventMapper.toResponse(event);
  }

  @Transactional
  public void delete(Long eventId) {
    Event event = findEntity(eventId);
    eventJpaRepository.delete(event);
  }

  private Event findEntity(Long eventId) {
    return eventJpaRepository.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));
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

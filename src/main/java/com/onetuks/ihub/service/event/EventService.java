package com.onetuks.ihub.service.event;

import com.onetuks.ihub.dto.event.EventCreateRequest;
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
  public Event create(EventCreateRequest request) {
    Event event = new Event();
    EventMapper.applyCreate(event, request);
    event.setProject(findProject(request.projectId()));
    if (request.createdById() != null) {
      event.setCreatedBy(findUser(request.createdById()));
    }
    return eventJpaRepository.save(event);
  }

  @Transactional(readOnly = true)
  public Event getById(Long eventId) {
    return findEntity(eventId);
  }

  @Transactional(readOnly = true)
  public List<Event> getAll() {
    return eventJpaRepository.findAll();
  }

  @Transactional
  public Event update(Long eventId, EventUpdateRequest request) {
    Event event = findEntity(eventId);
    EventMapper.applyUpdate(event, request);
    return event;
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

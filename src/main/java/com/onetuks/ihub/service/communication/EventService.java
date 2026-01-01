package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeRequest.EventAttendeeRequests;
import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.EventAttendeeMapper;
import com.onetuks.ihub.mapper.EventMapper;
import com.onetuks.ihub.repository.EventAttendeeJpaRepository;
import com.onetuks.ihub.repository.EventJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;

  private final AlarmService alarmService;

  private final EventJpaRepository eventJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final EventAttendeeJpaRepository eventAttendeeJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Event create(User currentUser, EventCreateRequest request) {
    projectMemberCheckComponent.checkIsProjectMember(currentUser, request.projectId());
    Event event = eventJpaRepository.save(
        EventMapper.applyCreate(currentUser, findProject(request.projectId()), request));
    alarmService.create(event);
    return event;
  }

  @Transactional(readOnly = true)
  public Event getById(User currentUser, String eventId) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser, event.getProject().getProjectId());
    return event;
  }

  @Transactional(readOnly = true)
  public Page<Event> getAll(User currentUser, String projectId, Pageable pageable) {
    projectMemberCheckComponent.checkIsProjectMember(currentUser, projectId);
    return eventJpaRepository.findAllByProject_ProjectId(projectId, pageable);
  }

  @Transactional
  public Event update(User currentUser, String eventId, EventUpdateRequest request) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());
    EventMapper.applyUpdate(event, request);
    alarmService.update(event);
    return event;
  }

  @Transactional
  public void delete(User currentUser, String eventId) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());
    alarmService.delete(event);
    eventJpaRepository.delete(event);
  }

  public List<EventAttendee> manageAttendees(
      User currentUser, String eventId, EventAttendeeRequests requests) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());

    eventAttendeeJpaRepository.deleteAllByEvent_EventId(eventId);

    List<EventAttendee> eventAttendees = requests.requests().stream()
        .map(request ->
            EventAttendeeMapper.applyCreate(
                event,
                userJpaRepository.findById(request.userId())
                    .orElseThrow(EntityNotFoundException::new),
                request))
        .toList();
    return eventAttendeeJpaRepository.saveAll(eventAttendees);
  }

  public Page<EventAttendee> getAllAttendees(User currentUser, String eventId, Pageable pageable) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());
    return eventAttendeeJpaRepository.findAllByEvent_EventId(eventId, pageable);
  }

  private Event findEntity(String eventId) {
    return eventJpaRepository.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

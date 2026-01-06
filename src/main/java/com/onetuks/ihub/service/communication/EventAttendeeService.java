package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest.EventAttendeeCreateRequests;
import com.onetuks.ihub.dto.communication.EventAttendeeUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.EventAttendeeMapper;
import com.onetuks.ihub.repository.EventAttendeeJpaRepository;
import com.onetuks.ihub.repository.EventJpaRepository;
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
public class EventAttendeeService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final EventAttendeeJpaRepository eventAttendeeRepository;
  private final EventJpaRepository eventRepository;
  private final UserJpaRepository userRepository;

  @Transactional
  public List<EventAttendee> manageAttendees(
      User currentUser, String eventId, EventAttendeeCreateRequests requests) {
    Event event = getEvent(currentUser, eventId);
    eventAttendeeRepository.deleteAllByEvent_EventId(eventId);
    List<EventAttendee> eventAttendees = requests.requests().stream()
        .map(request -> EventAttendeeMapper.applyCreate(event, findUser(request.userId()), request))
        .toList();
    return eventAttendeeRepository.saveAll(eventAttendees);
  }

  @Transactional
  public EventAttendee addAttendee(
      User currentUser, String eventId, EventAttendeeCreateRequest request) {
    return eventAttendeeRepository.save(
        EventAttendeeMapper.applyCreate(
            getEvent(currentUser, eventId), findUser(request.userId()), request));
  }

  @Transactional
  public EventAttendee updateAttendee(
      User currentUser, String eventAttendeeId, EventAttendeeUpdateRequest request) {
    EventAttendee eventAttendee = eventAttendeeRepository.findById(eventAttendeeId)
        .orElseThrow(EntityNotFoundException::new);

    projectMemberCheckComponent.checkIsProjectMember(
        currentUser, eventAttendee.getEvent().getProject().getProjectId());

    return EventAttendeeMapper.applyUpdate(eventAttendee, request);
  }

  @Transactional(readOnly = true)
  public Page<EventAttendee> getAllAttendees(User currentUser, String eventId, Pageable pageable) {
    Event event = findEvent(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());
    return eventAttendeeRepository.findAllByEvent_EventId(eventId, pageable);
  }

  private Event getEvent(User currentUser, String eventId) {
    Event event = findEvent(eventId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser, event.getProject().getProjectId());
    return event;
  }

  private Event findEvent(String eventId) {
    return eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);
  }

  private User findUser(String userId) {
    return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
  }
}

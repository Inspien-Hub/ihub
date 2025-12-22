package com.onetuks.ihub.service.event;

import com.onetuks.ihub.dto.event.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.event.EventAttendeeUpdateRequest;
import com.onetuks.ihub.entity.event.Event;
import com.onetuks.ihub.entity.event.EventAttendee;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.EventAttendeeMapper;
import com.onetuks.ihub.repository.EventAttendeeJpaRepository;
import com.onetuks.ihub.repository.EventJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventAttendeeService {

  private final EventAttendeeJpaRepository eventAttendeeJpaRepository;
  private final EventJpaRepository eventJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public EventAttendee create(EventAttendeeCreateRequest request) {
    EventAttendee attendee = new EventAttendee();
    EventAttendeeMapper.applyCreate(attendee, request);
    attendee.setEvent(findEvent(request.eventId()));
    attendee.setUser(findUser(request.userId()));
    return eventAttendeeJpaRepository.save(attendee);
  }

  @Transactional(readOnly = true)
  public EventAttendee getById(Long attendeeId) {
    return findEntity(attendeeId);
  }

  @Transactional(readOnly = true)
  public List<EventAttendee> getAll() {
    return eventAttendeeJpaRepository.findAll();
  }

  @Transactional
  public EventAttendee update(Long attendeeId, EventAttendeeUpdateRequest request) {
    EventAttendee attendee = findEntity(attendeeId);
    EventAttendeeMapper.applyUpdate(attendee, request);
    return attendee;
  }

  @Transactional
  public void delete(Long attendeeId) {
    EventAttendee attendee = findEntity(attendeeId);
    eventAttendeeJpaRepository.delete(attendee);
  }

  private EventAttendee findEntity(Long attendeeId) {
    return eventAttendeeJpaRepository.findById(attendeeId)
        .orElseThrow(() -> new EntityNotFoundException("Event attendee not found: " + attendeeId));
  }

  private Event findEvent(Long eventId) {
    return eventJpaRepository.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

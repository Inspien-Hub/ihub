package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.EventAttendee;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAttendeeJpaRepository extends JpaRepository<EventAttendee, String> {

  void deleteAllByEvent_EventId(String eventId);

  Page<EventAttendee> findAllByEvent_EventId(String eventId, Pageable pageable);

  List<EventAttendee> findAllByEvent_EventId(String eventId);
}

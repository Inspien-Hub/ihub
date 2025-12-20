package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.event.EventAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAttendeeJpaRepository extends JpaRepository<EventAttendee, Long> {
}

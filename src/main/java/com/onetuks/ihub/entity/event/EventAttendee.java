package com.onetuks.ihub.entity.event;

import com.onetuks.ihub.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_attendees")
@Getter
@Setter
@RequiredArgsConstructor
public class EventAttendee {

  @Id
  @Column(name = "event_attendee_id", nullable = false)
  private String eventAttendeeId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  private User user;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "attend_status")
  private EventAttendeeStatus attendStatus;
}

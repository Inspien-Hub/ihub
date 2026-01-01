package com.onetuks.ihub.entity.communication;

import static com.onetuks.ihub.entity.communication.Alarm.TABLE_NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

  public static final String TABLE_NAME = "alarms";

  @Id
  @Column(name = "alarm_id", nullable = false)
  private String alarmId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
  private Event event;

  @Column(name = "notify_at", nullable = false)
  private LocalDateTime notifyAt;
}

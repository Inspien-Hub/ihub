package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.entity.communication.Alarm;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.AlarmJpaRepository;
import com.onetuks.ihub.repository.EventAttendeeJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

  private final AlarmSender alarmSender;
  private final AlarmJpaRepository alarmRepository;
  private final EventAttendeeJpaRepository eventAttendeeRepository;

  @Scheduled(fixedDelay = 60000)
  @Transactional
  public void sendAlarm() {
    alarmRepository.findAll()
        .forEach(alarm -> {
          alarmSender.send(
              alarm,
              eventAttendeeRepository.findAllByEvent_EventId(alarm.getEvent().getEventId()));
          alarmRepository.delete(alarm);
        });
  }

  @Transactional
  public Alarm create(Event event) {
    return alarmRepository.save(
        new Alarm(
            UUIDProvider.provideUUID(Alarm.TABLE_NAME),
            event,
            event.getStartDatetime().minusMinutes(event.getRemindBeforeMinutes())));
  }

  @Transactional
  public Alarm update(Event event) {
    Alarm alarm = alarmRepository.findByEvent(event).orElseThrow(EntityNotFoundException::new);
    if (alarm.getNotifyAt().isAfter(LocalDateTime.now())) {
      return alarm;
    }

    alarm.setNotifyAt(event.getStartDatetime().minusMinutes(event.getRemindBeforeMinutes()));
    return alarm;
  }

  @Transactional
  public void delete(Event event) {
    alarmRepository.deleteByEvent(event);
  }
}

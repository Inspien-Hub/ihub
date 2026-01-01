package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Alarm;
import com.onetuks.ihub.entity.communication.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmJpaRepository extends JpaRepository<Alarm, String> {

  Optional<Alarm> findByEvent(Event event);

  void deleteByEvent(Event event);
}

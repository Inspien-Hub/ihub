package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Notice;
import com.onetuks.ihub.entity.communication.NoticeStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeJpaRepository extends JpaRepository<Notice, String> {

  List<Notice> findAllByProject_ProjectIdAndStatusNot(String projectId, NoticeStatus status);
}

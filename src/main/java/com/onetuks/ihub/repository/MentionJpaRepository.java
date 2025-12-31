package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Mention;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentionJpaRepository extends JpaRepository<Mention, String> {

  List<Mention> findAllByMentionedUser_UserId(String userId);
}

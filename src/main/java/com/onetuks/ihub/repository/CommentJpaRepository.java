package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}

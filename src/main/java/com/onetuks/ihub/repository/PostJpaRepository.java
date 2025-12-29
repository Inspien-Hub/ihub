package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, String> {

  Page<Post> findAllByProject_ProjectId(String projectId, Pageable pageable);
}

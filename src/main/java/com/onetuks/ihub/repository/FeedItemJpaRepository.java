package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.FeedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedItemJpaRepository extends JpaRepository<FeedItem, Long> {
}

package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.project.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentJpaRepository extends JpaRepository<Attachment, Long> {
}

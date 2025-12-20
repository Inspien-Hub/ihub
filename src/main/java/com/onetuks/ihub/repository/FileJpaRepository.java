package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpaRepository extends JpaRepository<File, Long> {
}

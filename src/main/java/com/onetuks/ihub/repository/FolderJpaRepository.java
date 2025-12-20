package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.file.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderJpaRepository extends JpaRepository<Folder, Long> {
}

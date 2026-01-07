package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.project.FavoriteProject;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteProjectJpaRepository extends JpaRepository<FavoriteProject, String> {

  Optional<FavoriteProject> findByProject_ProjectIdAndUser_UserId(String projectId, String userId);
}

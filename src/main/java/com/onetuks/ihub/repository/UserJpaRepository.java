package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);
}

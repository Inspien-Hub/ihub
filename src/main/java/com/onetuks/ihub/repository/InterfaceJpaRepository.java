package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.interfaces.Interface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceJpaRepository extends JpaRepository<Interface, Long> {
}

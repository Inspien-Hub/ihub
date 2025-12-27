package com.onetuks.ihub;

import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import com.onetuks.ihub.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

public class TestIHubApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(IHubApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }

}

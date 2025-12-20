package com.onetuks.ihub.service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.file.FolderCreateRequest;
import com.onetuks.ihub.dto.file.FolderResponse;
import com.onetuks.ihub.dto.file.FolderUpdateRequest;
import com.onetuks.ihub.entity.file.Folder;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.FolderJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class FolderServiceTest {

  @Autowired
  private FolderService folderService;

  @Autowired
  private FolderJpaRepository folderJpaRepository;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  private Project project;
  private User creator;

  @BeforeEach
  void setUp() {
    creator = ServiceTestDataFactory.createUser(userJpaRepository, "folder@user.com", "FolderUser");
    project = ServiceTestDataFactory.createProject(projectJpaRepository, creator, creator, "FolderProj");
  }

  @AfterEach
  void tearDown() {
    folderJpaRepository.deleteAll();
    projectJpaRepository.deleteAll();
    userJpaRepository.deleteAll();
  }

  @Test
  void createFolder_success() {
    FolderCreateRequest request = new FolderCreateRequest(
        project.getProjectId(),
        null,
        "Root",
        creator.getUserId());

    FolderResponse response = folderService.create(request);

    assertNotNull(response.folderId());
    assertEquals("Root", response.name());
    assertEquals(project.getProjectId(), response.projectId());
  }

  @Test
  void updateFolder_success() {
    FolderResponse created = folderService.create(new FolderCreateRequest(
        project.getProjectId(), null, "Parent", creator.getUserId()));
    FolderUpdateRequest updateRequest = new FolderUpdateRequest("ParentRenamed", null);

    FolderResponse updated = folderService.update(created.folderId(), updateRequest);

    assertEquals("ParentRenamed", updated.name());
  }

  @Test
  void getFolders_returnsAll() {
    folderService.create(new FolderCreateRequest(
        project.getProjectId(), null, "F1", creator.getUserId()));
    folderService.create(new FolderCreateRequest(
        project.getProjectId(), null, "F2", creator.getUserId()));

    assertEquals(2, folderService.getAll().size());
  }

  @Test
  void deleteFolder_success() {
    FolderResponse created = folderService.create(new FolderCreateRequest(
        project.getProjectId(), null, "Del", creator.getUserId()));

    folderService.delete(created.folderId());

    assertEquals(0, folderJpaRepository.count());
    assertThrows(EntityNotFoundException.class, () -> folderService.getById(created.folderId()));
  }
}

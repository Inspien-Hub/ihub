package com.onetuks.ihub.service.file;

import com.onetuks.ihub.dto.file.FolderCreateRequest;
import com.onetuks.ihub.dto.file.FolderUpdateRequest;
import com.onetuks.ihub.entity.file.Folder;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.FolderMapper;
import com.onetuks.ihub.repository.FolderJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FolderService {

  private final FolderJpaRepository folderJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Folder create(FolderCreateRequest request) {
    Folder folder = new Folder();
    FolderMapper.applyCreate(folder, request);
    folder.setProject(findProject(request.projectId()));
    if (request.parentFolderId() != null) {
      folder.setParentFolder(findFolder(request.parentFolderId()));
    }
    if (request.createdById() != null) {
      folder.setCreatedBy(findUser(request.createdById()));
    }
    return folderJpaRepository.save(folder);
  }

  @Transactional(readOnly = true)
  public Folder getById(Long folderId) {
    return findEntity(folderId);
  }

  @Transactional(readOnly = true)
  public List<Folder> getAll() {
    return folderJpaRepository.findAll();
  }

  @Transactional
  public Folder update(Long folderId, FolderUpdateRequest request) {
    Folder folder = findEntity(folderId);
    FolderMapper.applyUpdate(folder, request);
    if (request.parentFolderId() != null) {
      folder.setParentFolder(findFolder(request.parentFolderId()));
    }
    return folder;
  }

  @Transactional
  public void delete(Long folderId) {
    Folder folder = findEntity(folderId);
    folderJpaRepository.delete(folder);
  }

  private Folder findEntity(Long folderId) {
    return folderJpaRepository.findById(folderId)
        .orElseThrow(() -> new EntityNotFoundException("Folder not found: " + folderId));
  }

  private Folder findFolder(Long folderId) {
    return findEntity(folderId);
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

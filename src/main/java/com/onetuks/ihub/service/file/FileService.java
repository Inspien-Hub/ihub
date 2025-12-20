package com.onetuks.ihub.service.file;

import com.onetuks.ihub.dto.file.FileCreateRequest;
import com.onetuks.ihub.dto.file.FileResponse;
import com.onetuks.ihub.dto.file.FileUpdateRequest;
import com.onetuks.ihub.entity.file.File;
import com.onetuks.ihub.entity.file.Folder;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.FileMapper;
import com.onetuks.ihub.repository.FileJpaRepository;
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
public class FileService {

  private final FileJpaRepository fileJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final FolderJpaRepository folderJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public FileResponse create(FileCreateRequest request) {
    File file = new File();
    FileMapper.applyCreate(file, request);
    file.setProject(findProject(request.projectId()));
    if (request.folderId() != null) {
      file.setFolder(findFolder(request.folderId()));
    }
    if (request.uploadedById() != null) {
      file.setUploadedBy(findUser(request.uploadedById()));
    }
    File saved = fileJpaRepository.save(file);
    return FileMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public FileResponse getById(Long fileId) {
    return FileMapper.toResponse(findEntity(fileId));
  }

  @Transactional(readOnly = true)
  public List<FileResponse> getAll() {
    return fileJpaRepository.findAll().stream()
        .map(FileMapper::toResponse)
        .toList();
  }

  @Transactional
  public FileResponse update(Long fileId, FileUpdateRequest request) {
    File file = findEntity(fileId);
    FileMapper.applyUpdate(file, request);
    if (request.folderId() != null) {
      file.setFolder(findFolder(request.folderId()));
    }
    if (request.uploadedById() != null) {
      file.setUploadedBy(findUser(request.uploadedById()));
    }
    return FileMapper.toResponse(file);
  }

  @Transactional
  public void delete(Long fileId) {
    File file = findEntity(fileId);
    fileJpaRepository.delete(file);
  }

  private File findEntity(Long fileId) {
    return fileJpaRepository.findById(fileId)
        .orElseThrow(() -> new EntityNotFoundException("File not found: " + fileId));
  }

  private Project findProject(Long projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private Folder findFolder(Long folderId) {
    return folderJpaRepository.findById(folderId)
        .orElseThrow(() -> new EntityNotFoundException("Folder not found: " + folderId));
  }

  private User findUser(Long userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}

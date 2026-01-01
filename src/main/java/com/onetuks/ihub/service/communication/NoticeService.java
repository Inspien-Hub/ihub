package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.NoticeCreateRequest;
import com.onetuks.ihub.dto.communication.NoticeUpdateRequest;
import com.onetuks.ihub.entity.communication.Notice;
import com.onetuks.ihub.entity.communication.NoticeStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.NoticeMapper;
import com.onetuks.ihub.repository.NoticeJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final NoticeJpaRepository noticeJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public Notice create(User currentUser, String projectId, NoticeCreateRequest request) {
    Project project = findProject(projectId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, projectId);
    return noticeJpaRepository.save(NoticeMapper.applyCreate(currentUser, project, request));
  }

  @Transactional(readOnly = true)
  public Notice getById(User currentUser, String noticeId) {
    Notice notice = findEntity(noticeId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, notice.getProject().getProjectId());
    return notice;
  }

  @Transactional(readOnly = true)
  public Page<Notice> getAll(
      User currentUser,
      String projectId,
      String keyword,
      String authorId,
      String category,
      String importance,
      Boolean isPinned,
      LocalDateTime from,
      LocalDateTime to,
      Pageable pageable
  ) {
    projectMemberCheckComponent.checkIsProjectMember(currentUser, projectId);
    List<Notice> notices =
        noticeJpaRepository.findAllByProject_ProjectIdAndStatusNot(projectId, NoticeStatus.DELETED);

    List<Notice> filtered = notices.stream()
        .filter(notice -> keyword == null || keyword.isBlank()
            || containsIgnoreCase(notice.getTitle(), keyword)
            || containsIgnoreCase(notice.getContent(), keyword))
        .filter(notice -> authorId == null
            || (notice.getCreatedBy() != null && Objects.equals(authorId, notice.getCreatedBy().getUserId())))
        .filter(notice -> category == null || Objects.equals(category, notice.getCategory()))
        .filter(notice -> importance == null
            || notice.getImportance().name().equalsIgnoreCase(importance))
        .filter(notice -> isPinned == null || Objects.equals(isPinned, notice.getIsPinned()))
        .filter(notice -> from == null
            || (notice.getCreatedAt() != null && !notice.getCreatedAt().isBefore(from)))
        .filter(notice -> to == null
            || (notice.getCreatedAt() != null && !notice.getCreatedAt().isAfter(to)))
        .toList();

    List<Notice> pageContent = slice(filtered, pageable);
    return new PageImpl<>(pageContent, pageable, filtered.size());
  }

  @Transactional
  public Notice update(User currentUser, String noticeId, NoticeUpdateRequest request) {
    Notice notice = findEntity(noticeId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, notice.getProject().getProjectId());
    NoticeMapper.applyUpdate(notice, request, currentUser);
    return notice;
  }

  @Transactional
  public Notice delete(User currentUser, String noticeId) {
    Notice notice = findEntity(noticeId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, notice.getProject().getProjectId());
    notice.setStatus(NoticeStatus.DELETED);
    notice.setDeletedAt(LocalDateTime.now());
    notice.setUpdatedBy(currentUser);
    notice.setUpdatedAt(LocalDateTime.now());
    return notice;
  }

  private Notice findEntity(String noticeId) {
    return noticeJpaRepository.findById(noticeId)
        .orElseThrow(() -> new EntityNotFoundException("Notice not found: " + noticeId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private boolean containsIgnoreCase(String text, String keyword) {
    return text != null && text.toLowerCase().contains(keyword.toLowerCase());
  }

  private List<Notice> slice(List<Notice> notices, Pageable pageable) {
    int start = (int) pageable.getOffset();
    if (start >= notices.size()) {
      return List.of();
    }
    int end = Math.min(start + pageable.getPageSize(), notices.size());
    return notices.subList(start, end);
  }
}

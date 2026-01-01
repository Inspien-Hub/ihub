package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.NoticeCreateRequest;
import com.onetuks.ihub.dto.communication.NoticeResponse;
import com.onetuks.ihub.dto.communication.NoticeUpdateRequest;
import com.onetuks.ihub.entity.communication.Notice;
import com.onetuks.ihub.mapper.NoticeMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.NoticeService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeRestControllerImpl implements NoticeRestController {

  private final CurrentUserProvider currentUserProvider;
  private final NoticeService noticeService;

  @Override
  public ResponseEntity<Page<NoticeResponse>> getNotices(
      @PathVariable String projectId,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String authorId,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String importance,
      @RequestParam(required = false) Boolean isPinned,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime from,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime to,
      @PageableDefault Pageable pageable
  ) {
    Page<Notice> results = noticeService.getAll(
        currentUserProvider.resolveUser(),
        projectId,
        keyword,
        authorId,
        category,
        importance,
        isPinned,
        from,
        to,
        pageable);
    Page<NoticeResponse> responses = results.map(NoticeMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<NoticeResponse> createNotice(
      @PathVariable String projectId,
      @Valid @RequestBody NoticeCreateRequest request
  ) {
    Notice result = noticeService.create(currentUserProvider.resolveUser(), projectId, request);
    NoticeResponse response = NoticeMapper.toResponse(result);
    return ResponseEntity.created(URI.create("/api/notices/" + response.noticeId()))
        .body(response);
  }

  @Override
  public ResponseEntity<NoticeResponse> getNotice(@PathVariable String noticeId) {
    Notice result = noticeService.getById(currentUserProvider.resolveUser(), noticeId);
    NoticeResponse response = NoticeMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<NoticeResponse> updateNotice(
      @PathVariable String noticeId,
      @Valid @RequestBody NoticeUpdateRequest request
  ) {
    Notice result = noticeService.update(currentUserProvider.resolveUser(), noticeId, request);
    NoticeResponse response = NoticeMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteNotice(@PathVariable String noticeId) {
    noticeService.delete(currentUserProvider.resolveUser(), noticeId);
    return ResponseEntity.noContent().build();
  }
}

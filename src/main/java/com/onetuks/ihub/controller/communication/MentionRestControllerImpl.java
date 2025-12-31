package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.MentionResponse;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.mapper.MentionMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.MentionService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MentionRestControllerImpl implements MentionRestController {

  private final CurrentUserProvider currentUserProvider;
  private final MentionService mentionService;

  @Override
  public ResponseEntity<Page<MentionResponse>> getMyMentions(
      @RequestParam(required = false) String projectId,
      @RequestParam(required = false) List<String> authorIds,
      @RequestParam(required = false) List<TargetType> targetTypes,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime from,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime to,
      @PageableDefault Pageable pageable
  ) {
    Page<MentionResponse> responses = mentionService.getMyMentions(
            currentUserProvider.resolveUser(),
            projectId,
            authorIds,
            targetTypes,
            from,
            to,
            pageable)
        .map(MentionMapper::toResponse);
    return ResponseEntity.ok(responses);
  }
}

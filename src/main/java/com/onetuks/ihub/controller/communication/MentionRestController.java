package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.MentionResponse;
import com.onetuks.ihub.entity.communication.TargetType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/users/me/mentions")
@Tag(name = "Mention", description = "언급 조회 API")
public interface MentionRestController {

  @Operation(summary = "내 언급 조회", description = "내가 언급된 항목을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "언급 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping
  ResponseEntity<Page<MentionResponse>> getMyMentions(
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
  );
}

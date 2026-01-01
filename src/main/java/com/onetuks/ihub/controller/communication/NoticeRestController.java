package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.NoticeCreateRequest;
import com.onetuks.ihub.dto.communication.NoticeResponse;
import com.onetuks.ihub.dto.communication.NoticeUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api")
@Tag(name = "Notice", description = "공지사항 관리 API")
public interface NoticeRestController {

  @Operation(summary = "공지사항 목록 조회", description = "프로젝트 공지사항을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "공지사항 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping("/projects/{projectId}/notices")
  ResponseEntity<Page<NoticeResponse>> getNotices(
      @PathVariable String projectId,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String authorId,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String importance,
      @RequestParam(required = false) Boolean isPinned,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
      @PageableDefault Pageable pageable
  );

  @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "공지사항 생성 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PostMapping("/projects/{projectId}/notices")
  ResponseEntity<NoticeResponse> createNotice(
      @PathVariable String projectId,
      @Valid @RequestBody NoticeCreateRequest request
  );

  @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세 내용을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "공지사항 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping("/notices/{noticeId}")
  ResponseEntity<NoticeResponse> getNotice(@PathVariable String noticeId);

  @Operation(summary = "공지사항 수정", description = "공지사항의 내용을 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "공지사항 수정 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PatchMapping("/notices/{noticeId}")
  ResponseEntity<NoticeResponse> updateNotice(
      @PathVariable String noticeId,
      @Valid @RequestBody NoticeUpdateRequest request
  );

  @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "공지사항 삭제 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @DeleteMapping("/notices/{noticeId}")
  ResponseEntity<Void> deleteNotice(@PathVariable String noticeId);
}

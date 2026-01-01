package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.NoticeImportance;
import com.onetuks.ihub.entity.communication.NoticeStatus;
import java.time.LocalDateTime;

public record NoticeResponse(
    String noticeId,
    String projectId,
    String title,
    String content,
    String category,
    NoticeImportance importance,
    Boolean isPinned,
    LocalDateTime pinnedAt,
    NoticeStatus status,
    String createdById,
    String updatedById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}

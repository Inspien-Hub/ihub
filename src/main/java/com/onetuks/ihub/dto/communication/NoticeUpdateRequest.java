package com.onetuks.ihub.dto.communication;

public record NoticeUpdateRequest(
    String title,
    String content,
    String category,
    String importance,
    Boolean isPinned
) {

}

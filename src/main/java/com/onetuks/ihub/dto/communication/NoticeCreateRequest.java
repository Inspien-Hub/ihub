package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotBlank;

public record NoticeCreateRequest(
    @NotBlank String title,
    @NotBlank String content,
    String category,
    String importance,
    Boolean isPinned
) {

}

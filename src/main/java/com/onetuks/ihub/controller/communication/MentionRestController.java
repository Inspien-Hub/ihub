package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.MentionCreateRequest;
import com.onetuks.ihub.dto.communication.MentionResponse;
import com.onetuks.ihub.dto.communication.MentionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/mentions")
@Tag(name = "Mention", description = "Mention management APIs")
public interface MentionRestController {

  @Operation(summary = "Create mention")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Mention created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<MentionResponse> createMention(@Valid @RequestBody MentionCreateRequest request);

  @Operation(summary = "Get mention by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Mention found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Mention not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{mentionId}")
  ResponseEntity<MentionResponse> getMention(@PathVariable Long mentionId);

  @Operation(summary = "List mentions")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Mentions listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<MentionResponse>> getMentions();

  @Operation(summary = "Update mention")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Mention updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Mention not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{mentionId}")
  ResponseEntity<MentionResponse> updateMention(
      @PathVariable Long mentionId,
      @Valid @RequestBody MentionUpdateRequest request);

  @Operation(summary = "Delete mention")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Mention deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Mention not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{mentionId}")
  ResponseEntity<Void> deleteMention(@PathVariable Long mentionId);
}

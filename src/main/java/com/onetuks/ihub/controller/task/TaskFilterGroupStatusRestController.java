package com.onetuks.ihub.controller.task;

import com.onetuks.ihub.dto.task.TaskFilterGroupStatusCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusResponse;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusUpdateRequest;
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

@RequestMapping("/api/task-filter-group-statuses")
@Tag(name = "TaskFilterGroupStatus", description = "Task filter group status management APIs")
public interface TaskFilterGroupStatusRestController {

  @Operation(summary = "Create task filter group status")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Task filter group status created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<TaskFilterGroupStatusResponse> createTaskFilterGroupStatus(
      @Valid @RequestBody TaskFilterGroupStatusCreateRequest request);

  @Operation(summary = "Get task filter group status by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task filter group status found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Task filter group status not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{statusId}")
  ResponseEntity<TaskFilterGroupStatusResponse> getTaskFilterGroupStatus(
      @PathVariable Long statusId);

  @Operation(summary = "List task filter group statuses")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task filter group statuses listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<TaskFilterGroupStatusResponse>> getTaskFilterGroupStatuses();

  @Operation(summary = "Update task filter group status")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task filter group status updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Task filter group status not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{statusId}")
  ResponseEntity<TaskFilterGroupStatusResponse> updateTaskFilterGroupStatus(
      @PathVariable Long statusId,
      @Valid @RequestBody TaskFilterGroupStatusUpdateRequest request);

  @Operation(summary = "Delete task filter group status")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Task filter group status deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Task filter group status not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{statusId}")
  ResponseEntity<Void> deleteTaskFilterGroupStatus(@PathVariable Long statusId);
}

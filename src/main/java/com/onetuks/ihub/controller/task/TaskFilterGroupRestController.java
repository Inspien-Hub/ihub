package com.onetuks.ihub.controller.task;

import com.onetuks.ihub.dto.task.TaskFilterGroupCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupResponse;
import com.onetuks.ihub.dto.task.TaskFilterGroupUpdateRequest;
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

@RequestMapping("/api/task-filter-groups")
@Tag(name = "TaskFilterGroup", description = "Task filter group management APIs")
public interface TaskFilterGroupRestController {

  @Operation(summary = "Create task filter group")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Task filter group created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<TaskFilterGroupResponse> createTaskFilterGroup(
      @Valid @RequestBody TaskFilterGroupCreateRequest request);

  @Operation(summary = "Get task filter group by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task filter group found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Task filter group not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{groupId}")
  ResponseEntity<TaskFilterGroupResponse> getTaskFilterGroup(@PathVariable Long groupId);

  @Operation(summary = "List task filter groups")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task filter groups listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<TaskFilterGroupResponse>> getTaskFilterGroups();

  @Operation(summary = "Update task filter group")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task filter group updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Task filter group not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{groupId}")
  ResponseEntity<TaskFilterGroupResponse> updateTaskFilterGroup(
      @PathVariable Long groupId,
      @Valid @RequestBody TaskFilterGroupUpdateRequest request);

  @Operation(summary = "Delete task filter group")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Task filter group deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Task filter group not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{groupId}")
  ResponseEntity<Void> deleteTaskFilterGroup(@PathVariable Long groupId);
}

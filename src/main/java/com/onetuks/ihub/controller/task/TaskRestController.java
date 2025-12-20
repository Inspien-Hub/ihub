package com.onetuks.ihub.controller.task;

import com.onetuks.ihub.dto.task.TaskCreateRequest;
import com.onetuks.ihub.dto.task.TaskResponse;
import com.onetuks.ihub.dto.task.TaskUpdateRequest;
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

@RequestMapping("/api/tasks")
@Tag(name = "Task", description = "Task management APIs")
public interface TaskRestController {

  @Operation(summary = "Create task")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Task created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest request);

  @Operation(summary = "Get task by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Task not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{taskId}")
  ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId);

  @Operation(summary = "List tasks")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Tasks listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<TaskResponse>> getTasks();

  @Operation(summary = "Update task")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Task updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Task not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{taskId}")
  ResponseEntity<TaskResponse> updateTask(
      @PathVariable Long taskId,
      @Valid @RequestBody TaskUpdateRequest request);

  @Operation(summary = "Delete task")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Task deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Task not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{taskId}")
  ResponseEntity<Void> deleteTask(@PathVariable Long taskId);
}

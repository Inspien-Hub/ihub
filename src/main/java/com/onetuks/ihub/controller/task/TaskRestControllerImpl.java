package com.onetuks.ihub.controller.task;

import com.onetuks.ihub.dto.task.TaskCreateRequest;
import com.onetuks.ihub.dto.task.TaskResponse;
import com.onetuks.ihub.dto.task.TaskUpdateRequest;
import com.onetuks.ihub.service.task.TaskService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskRestControllerImpl implements TaskRestController {

  private final TaskService taskService;

  @Override
  public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest request) {
    TaskResponse response = taskService.create(request);
    return ResponseEntity.created(URI.create("/api/tasks/" + response.taskId()))
        .body(response);
  }

  @Override
  public ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId) {
    return ResponseEntity.ok(taskService.getById(taskId));
  }

  @Override
  public ResponseEntity<List<TaskResponse>> getTasks() {
    return ResponseEntity.ok(taskService.getAll());
  }

  @Override
  public ResponseEntity<TaskResponse> updateTask(
      @PathVariable Long taskId, @Valid @RequestBody TaskUpdateRequest request) {
    return ResponseEntity.ok(taskService.update(taskId, request));
  }

  @Override
  public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
    taskService.delete(taskId);
    return ResponseEntity.noContent().build();
  }
}

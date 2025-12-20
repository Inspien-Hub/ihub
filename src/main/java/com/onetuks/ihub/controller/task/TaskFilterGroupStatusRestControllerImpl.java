package com.onetuks.ihub.controller.task;

import com.onetuks.ihub.dto.task.TaskFilterGroupStatusCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusResponse;
import com.onetuks.ihub.dto.task.TaskFilterGroupStatusUpdateRequest;
import com.onetuks.ihub.service.task.TaskFilterGroupStatusService;
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
public class TaskFilterGroupStatusRestControllerImpl
    implements TaskFilterGroupStatusRestController {

  private final TaskFilterGroupStatusService taskFilterGroupStatusService;

  @Override
  public ResponseEntity<TaskFilterGroupStatusResponse> createTaskFilterGroupStatus(
      @Valid @RequestBody TaskFilterGroupStatusCreateRequest request) {
    TaskFilterGroupStatusResponse response = taskFilterGroupStatusService.create(request);
    return ResponseEntity.created(URI.create("/api/task-filter-group-statuses/" + response.statusId()))
        .body(response);
  }

  @Override
  public ResponseEntity<TaskFilterGroupStatusResponse> getTaskFilterGroupStatus(Long statusId) {
    return ResponseEntity.ok(taskFilterGroupStatusService.getById(statusId));
  }

  @Override
  public ResponseEntity<List<TaskFilterGroupStatusResponse>> getTaskFilterGroupStatuses() {
    return ResponseEntity.ok(taskFilterGroupStatusService.getAll());
  }

  @Override
  public ResponseEntity<TaskFilterGroupStatusResponse> updateTaskFilterGroupStatus(
      Long statusId, @Valid @RequestBody TaskFilterGroupStatusUpdateRequest request) {
    return ResponseEntity.ok(taskFilterGroupStatusService.update(statusId, request));
  }

  @Override
  public ResponseEntity<Void> deleteTaskFilterGroupStatus(Long statusId) {
    taskFilterGroupStatusService.delete(statusId);
    return ResponseEntity.noContent().build();
  }
}

package com.onetuks.ihub.controller.task;

import com.onetuks.ihub.dto.task.TaskFilterGroupCreateRequest;
import com.onetuks.ihub.dto.task.TaskFilterGroupResponse;
import com.onetuks.ihub.dto.task.TaskFilterGroupUpdateRequest;
import com.onetuks.ihub.service.task.TaskFilterGroupService;
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
public class TaskFilterGroupRestControllerImpl implements TaskFilterGroupRestController {

  private final TaskFilterGroupService taskFilterGroupService;

  @Override
  public ResponseEntity<TaskFilterGroupResponse> createTaskFilterGroup(
      @Valid @RequestBody TaskFilterGroupCreateRequest request) {
    TaskFilterGroupResponse response = taskFilterGroupService.create(request);
    return ResponseEntity.created(URI.create("/api/task-filter-groups/" + response.groupId()))
        .body(response);
  }

  @Override
  public ResponseEntity<TaskFilterGroupResponse> getTaskFilterGroup(@PathVariable Long groupId) {
    return ResponseEntity.ok(taskFilterGroupService.getById(groupId));
  }

  @Override
  public ResponseEntity<List<TaskFilterGroupResponse>> getTaskFilterGroups() {
    return ResponseEntity.ok(taskFilterGroupService.getAll());
  }

  @Override
  public ResponseEntity<TaskFilterGroupResponse> updateTaskFilterGroup(
      @PathVariable Long groupId, @Valid @RequestBody TaskFilterGroupUpdateRequest request) {
    return ResponseEntity.ok(taskFilterGroupService.update(groupId, request));
  }

  @Override
  public ResponseEntity<Void> deleteTaskFilterGroup(@PathVariable Long groupId) {
    taskFilterGroupService.delete(groupId);
    return ResponseEntity.noContent().build();
  }
}

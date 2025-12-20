package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryUpdateRequest;
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

@RequestMapping("/api/interface-status-histories")
@Tag(name = "InterfaceStatusHistory", description = "Interface status history management APIs")
public interface InterfaceStatusHistoryRestController {

  @Operation(summary = "Create interface status history")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Interface status history created"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<InterfaceStatusHistoryResponse> createInterfaceStatusHistory(
      @Valid @RequestBody InterfaceStatusHistoryCreateRequest request);

  @Operation(summary = "Get interface status history by id")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface status history found"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface status history not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{historyId}")
  ResponseEntity<InterfaceStatusHistoryResponse> getInterfaceStatusHistory(
      @PathVariable Long historyId);

  @Operation(summary = "List interface status histories")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface status histories listed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<List<InterfaceStatusHistoryResponse>> getInterfaceStatusHistories();

  @Operation(summary = "Update interface status history")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Interface status history updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "404", description = "Interface status history not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{historyId}")
  ResponseEntity<InterfaceStatusHistoryResponse> updateInterfaceStatusHistory(
      @PathVariable Long historyId,
      @Valid @RequestBody InterfaceStatusHistoryUpdateRequest request);

  @Operation(summary = "Delete interface status history")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Interface status history deleted"),
    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
    @ApiResponse(responseCode = "404", description = "Interface status history not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{historyId}")
  ResponseEntity<Void> deleteInterfaceStatusHistory(@PathVariable Long historyId);
}

package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.ChannelAdapter;
import com.onetuks.ihub.entity.interfaces.InterfaceType;
import com.onetuks.ihub.entity.interfaces.SyncAsyncType;
import jakarta.validation.constraints.NotNull;

public record InterfaceCreateRequest(
    @NotNull Long projectId,
    String ifId,
    @NotNull Long sourceSystemId,
    @NotNull Long targetSystemId,
    String module,
    @NotNull InterfaceType interfaceType,
    String pattern,
    @NotNull ChannelAdapter senderAdapter,
    @NotNull ChannelAdapter receiverAdapter,
    @NotNull SyncAsyncType sa,
    @NotNull Long statusId,
    String batchTimeLabel,
    String remark,
    @NotNull Long createdById
) {
}

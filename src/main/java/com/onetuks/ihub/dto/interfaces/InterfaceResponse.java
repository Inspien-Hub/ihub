package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.ChannelAdapter;
import com.onetuks.ihub.entity.interfaces.InterfaceType;
import com.onetuks.ihub.entity.interfaces.SyncAsyncType;
import java.time.LocalDateTime;

public record InterfaceResponse(
    Long interfaceId,
    Long projectId,
    String ifId,
    Long sourceSystemId,
    Long targetSystemId,
    String module,
    InterfaceType interfaceType,
    String pattern,
    ChannelAdapter senderAdapter,
    ChannelAdapter receiverAdapter,
    SyncAsyncType sa,
    Long statusId,
    String batchTimeLabel,
    String remark,
    Long createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

package com.lel.bookmingle.dto.request;

import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.enums.RequestType;
import com.lel.bookmingle.utility.context.ContextManager;

public record ExchangeRequest(
        Integer requesterUserId,
        Integer proposedBookId,
        Integer requestedUserId,
        Integer requestedBookId,
        RequestType requestType,
        RequestStatus requestStatus

        ) {
    public ExchangeRequest {
        requesterUserId = ContextManager.get().getUser().getId();
        requestType = RequestType.EXCHANGE;
        requestStatus = RequestStatus.PENDING;
    }
}

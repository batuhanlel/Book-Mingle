package com.lel.bookmingle.dto.response;

import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.enums.RequestType;

import java.time.LocalDate;

public record ExchangeDemandResponse(
        Long requestId,
        RequestStatus requestStatus,
        RequestType requestType,
        LocalDate requestDate,
        LocalDate acceptedDate,
        BookResponse proposedBook,
        BookResponse requestedBook,
        UserResponse requesterUser
) {
}

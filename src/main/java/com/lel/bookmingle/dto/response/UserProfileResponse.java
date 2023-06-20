package com.lel.bookmingle.dto.response;

import java.util.List;

public record UserProfileResponse(
        String email,
        String name,
        String surname,
        Integer bookCount,
        Integer successfulExchangeCount,
        List<BookResponse> books
) {
}

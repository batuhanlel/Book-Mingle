package com.lel.bookmingle.dto.response;

public record UserResponse(
        Integer id,
        String name,
        String surname,
        String email
) {
}

package com.lel.bookmingle.dto.request.authorization;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email Can Not be Empty")
        String email,
        @NotBlank(message = "Password Can Not Be Empty")
        String password
) {
}

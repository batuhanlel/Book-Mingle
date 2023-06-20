package com.lel.bookmingle.dto.response;


import java.time.LocalDateTime;

public record ChatResponse(
        Long chatId,
        UserResponse receiver,
        String lastMessage,
        LocalDateTime updatedAt
) {
}

package com.lel.bookmingle.dto.response;

public record ChatMessageResponse(
        int senderId,
        int receiverId,
        String content
) {
}

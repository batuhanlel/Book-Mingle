package com.lel.bookmingle.dto.request;


import java.time.LocalDateTime;

public record ChatMessageRequest(
        Integer senderId,
        Integer receiverId,
        String content,
        LocalDateTime sentAt
) {
    public ChatMessageRequest {
        sentAt = LocalDateTime.now();
    }
}

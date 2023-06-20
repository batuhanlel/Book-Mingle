package com.lel.bookmingle.dto.response;

public record BookExchangeResponse(

        Integer bookId,
        String title,
        String author,
        String imageUrl,
        Integer userId,
        String userName,
        String userSurname,
        String userEmail
) {
}

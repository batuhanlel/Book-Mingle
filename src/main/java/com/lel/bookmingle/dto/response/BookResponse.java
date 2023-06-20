package com.lel.bookmingle.dto.response;

public record BookResponse(
        Integer id,
        String title,
        String author,
        String publisher,
        String imageUrl
) {
}

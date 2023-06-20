package com.lel.bookmingle.dto.request.book;

public record BookSearchRequest(
        String searchText,
        Boolean hideDeleted,
        Integer page,
        Integer length,
        String sort
) {
}

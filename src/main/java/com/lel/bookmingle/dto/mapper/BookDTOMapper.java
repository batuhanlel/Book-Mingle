package com.lel.bookmingle.dto.mapper;

import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.model.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookDTOMapper implements Function<Book, BookResponse> {
    @Override
    public BookResponse apply(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getImageUrl()
        );
    }
}

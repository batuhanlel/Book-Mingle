package com.lel.bookmingle.controller;

import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    @GetMapping("{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponse>> getBookList(@Param("search") String search) {
        return ResponseEntity.ok(bookService.getBookList(search));
    }

}

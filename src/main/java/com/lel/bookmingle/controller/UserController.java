package com.lel.bookmingle.controller;

import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.dto.response.UserProfileResponse;
import com.lel.bookmingle.dto.response.UserResponse;
import com.lel.bookmingle.service.BookService;
import com.lel.bookmingle.service.UserService;
import com.lel.bookmingle.utility.context.ContextProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private BookService bookService;
    private ContextProvider contextProvider;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMe() {
        Integer id = contextProvider.get().getUser().getId();
        return ResponseEntity.ok(userService.getUserProfile(id));
    }

    @GetMapping("/about")
    public ResponseEntity<UserResponse> getAbout() {
        Integer id = contextProvider.get().getUser().getId();
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(bookService.getUserBookList());
    }


    @PostMapping("/add/book/{bookId}")
    public ResponseEntity<String> add(@PathVariable("bookId") Integer bookId) {
        userService.addBookToUserLibrary(bookId);
        return ResponseEntity.ok().body("Book Added to User Successfully");
    }
}

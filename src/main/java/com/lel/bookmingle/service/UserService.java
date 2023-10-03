package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.mapper.BookDTOMapper;
import com.lel.bookmingle.dto.mapper.UserDTOMapper;
import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.dto.response.UserProfileResponse;
import com.lel.bookmingle.dto.response.UserResponse;
import com.lel.bookmingle.exception.ModelNotFoundException;
import com.lel.bookmingle.model.Book;
import com.lel.bookmingle.model.User;
import com.lel.bookmingle.repository.IUserRepository;
import com.lel.bookmingle.utility.context.ContextProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lel.bookmingle.utility.Constants.ExceptionMessages.NO_SUCH_USER;

@Service
@AllArgsConstructor
public class UserService {

    private ContextProvider contextProvider;
    private BookExchangeService bookExchangeService;
    private BookService bookService;
    private IUserRepository userRepository;
    private UserDTOMapper userDTOMapper;
    private BookDTOMapper bookDTOMapper;

    public UserProfileResponse getUserProfile(int id) {
        User user = findUserById(id);
        List<BookResponse> bookList = user.getBooks().stream().map(bookDTOMapper).toList();
        Integer exchangeCount = bookExchangeService.getSuccessfulExchangeCount(id);

        return new UserProfileResponse(
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getBooks().size(),
                exchangeCount,
                bookList
        );
    }

    @Transactional
    public void addBookToUserLibrary(Integer bookId) {
        User user = findUserById(contextProvider.get().getUser().getId());
        Book book = bookService.findBookById(bookId);
        user.getBooks().add(book);
        userRepository.save(user);
    }

    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new ModelNotFoundException(NO_SUCH_USER));
    }

    @Transactional
    public void saveUser(User newUser) {
        userRepository.save(newUser);
    }

    protected User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(NO_SUCH_USER));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ModelNotFoundException(NO_SUCH_USER));
    }
}

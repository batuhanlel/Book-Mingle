package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.mapper.BookDTOMapper;
import com.lel.bookmingle.dto.request.book.BookSearchRequest;
import com.lel.bookmingle.dto.response.BookExchangeResponse;
import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.exception.ModelNotFoundException;
import com.lel.bookmingle.model.Book;
import com.lel.bookmingle.model.User;
import com.lel.bookmingle.repository.IBookRepository;
import com.lel.bookmingle.utility.context.ContextProvider;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lel.bookmingle.utility.Constants.ExceptionMessages.NO_SUCH_BOOK;

@Service
@AllArgsConstructor
public class BookService {

    private IBookRepository bookRepository;
    private BookDTOMapper bookDTOMapper;
    private ContextProvider contextProvider;

    public List<BookExchangeResponse> getBookExchangeList(BookSearchRequest request) {
        Sort sort = getSort(request.sort());
        Pageable pageable = PageRequest.of(request.page(), request.length(), sort);
        return bookRepository.findBookExchangeList(pageable, request.searchText(), request.searchText());
    }

    public List<BookExchangeResponse> getBookExchangeListForRecommendations(User user, List<String> recommendedBookNames) {
        return bookRepository.findBookExchangeListForRecommendations(user.getId(), recommendedBookNames, user.getLatitude(), user.getLongitude(), 8);
    }

    public List<BookResponse> getBookList(String searchText) {
        Pageable pageable = PageRequest.of(0, 50);
        return bookRepository.findAllBySearchText(pageable, searchText, searchText, searchText);
    }

    public List<BookResponse> getUserBookList() {
        Integer userId = contextProvider.get().getUser().getId();
        return bookRepository.findBooksByUser(userId);
    }

    public BookResponse getBookById(Integer id) {
        return bookRepository.findById(id)
                .map(bookDTOMapper)
                .orElseThrow(() -> new ModelNotFoundException(NO_SUCH_BOOK));
    }

    protected Book findBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(NO_SUCH_BOOK));
    }

    private Sort getSort(String sort) {
        if (sort.startsWith("-")) {
            return Sort.by(sort.substring(1)).ascending();
        } else {
            return Sort.by(sort).descending();
        }
    }
}

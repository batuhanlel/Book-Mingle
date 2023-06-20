package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.mapper.ExchangeDemandDTOMapper;
import com.lel.bookmingle.dto.request.ExchangeRequest;
import com.lel.bookmingle.dto.response.BookExchangeResponse;
import com.lel.bookmingle.dto.response.ExchangeDemandResponse;
import com.lel.bookmingle.dto.response.RecommendationApiResponse;
import com.lel.bookmingle.model.Book;
import com.lel.bookmingle.model.BookExchange;
import com.lel.bookmingle.model.User;
import com.lel.bookmingle.utility.context.ContextManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserBookExchangeService {

    private UserService userService;
    private BookService bookService;
    private BookExchangeService bookExchangeService;
    private RecommendationService recommendationService;
    private ExchangeDemandDTOMapper exchangeDemandDTOMapper;

    public boolean createExchangeRequest(ExchangeRequest request) {
        BookExchange createdRequest = bookExchangeService.createExchangeRequest(BookExchange.builder()
                .requesterUser(userService.findUserById(request.requesterUserId()))
                .proposedBook(bookService.findBookById(request.proposedBookId()))

                .requestedUser(userService.findUserById(request.requestedUserId()))
                .requestedBook(bookService.findBookById(request.requestedBookId()))

                .requestedDate(LocalDate.now())
                .requestType(request.requestType())
                .requestStatus(request.requestStatus())
                .build());
        return Objects.nonNull(createdRequest.getRequestId());
    }

    public List<ExchangeDemandResponse> getExchangeDemandList(Integer page) {
        List<BookExchange> list = bookExchangeService.getExchangeDemandListByUser(page);
        return list.stream().map(exchangeDemandDTOMapper).toList();
    }

    public List<BookExchangeResponse> getBookRecommendations(double latitude, double longitude) {
        List<String> userBooks = getUserBooks(latitude, longitude);

        RecommendationApiResponse recommendationApiResponse = recommendationService.getRecommendationList(userBooks);
        User user = userService.findUserById(ContextManager.get().getUser().getId());
        return bookService.getBookExchangeListForRecommendations(user, recommendationApiResponse.recommendations());
    }

    @Transactional
    private List<String> getUserBooks(double latitude, double longitude) {
        Integer userId = ContextManager.get().getUser().getId();
        User user = userService.findUserById(userId);


        user.setLatitude(latitude);
        user.setLongitude(longitude);
        userService.saveUser(user);

        return user.getBooks().stream().map(Book::getTitle).toList();
    }
}

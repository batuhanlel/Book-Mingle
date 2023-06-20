package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.request.book.BookSearchRequest;
import com.lel.bookmingle.dto.response.BookExchangeResponse;
import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.exception.ModelNotFoundException;
import com.lel.bookmingle.model.BookExchange;
import com.lel.bookmingle.model.User;
import com.lel.bookmingle.repository.IBookRequestRepository;
import com.lel.bookmingle.utility.context.ContextManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.lel.bookmingle.utility.Constants.ExceptionMessages.NO_SUCH_DEMAND;

@Service
@AllArgsConstructor
public class BookExchangeService {

    private IBookRequestRepository bookRequestRepository;
    private BookService bookService;
    private ChatService chatService;

    public List<BookExchangeResponse> getBookExchangeList(BookSearchRequest request) {
        return bookService.getBookExchangeList(request);
    }

    public void updateExchangeDemand(int demandId, boolean isAccepted) throws Exception {
        BookExchange exchangeDemand = findExchangeDemandById(demandId);
        if (isAccepted) {
            exchangeDemand.setRequestStatus(RequestStatus.ACCEPTED);
            boolean isSuccessful = chatService.createChat(exchangeDemand.getRequesterUser(), exchangeDemand.getRequestedUser());
            if (Boolean.FALSE.equals(isSuccessful)) {
                throw new Exception();
            }
        } else {
            exchangeDemand.setRequestStatus(RequestStatus.REJECTED);
        }
        exchangeDemand.setUpdatedDate(LocalDate.now());
        bookRequestRepository.save(exchangeDemand);
    }

    public Integer getSuccessfulExchangeCount(int userId) {
        return bookRequestRepository.countAcceptedRequestsByRequesterId(userId);
    }

    protected List<BookExchange> getExchangeDemandListByUser(Integer page) {
        User user = ContextManager.get().getUser();
        Sort sort = Sort.by("requestId");
        Pageable pageable = PageRequest.of(page, 10, sort);
        return bookRequestRepository.findByRequestedUserAndRequestStatus(user, RequestStatus.PENDING, pageable);
    }

    protected BookExchange createExchangeRequest(BookExchange exchange) {
        return bookRequestRepository.save(exchange);
    }

    protected BookExchange findExchangeDemandById(Integer id) {
        return bookRequestRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(NO_SUCH_DEMAND));
    }
}

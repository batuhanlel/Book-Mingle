package com.lel.bookmingle.dto.mapper;

import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.dto.response.ExchangeDemandResponse;
import com.lel.bookmingle.dto.response.UserResponse;
import com.lel.bookmingle.model.BookExchange;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExchangeDemandDTOMapper implements Function<BookExchange, ExchangeDemandResponse> {
    @Override
    public ExchangeDemandResponse apply(BookExchange exchange) {
        return new ExchangeDemandResponse(
                exchange.getRequestId(),
                exchange.getRequestStatus(),
                exchange.getRequestType(),
                exchange.getRequestedDate(),
                exchange.getUpdatedDate(),

                new BookResponse(
                        exchange.getProposedBook().getId(),
                        exchange.getProposedBook().getTitle(),
                        exchange.getProposedBook().getAuthor(),
                        exchange.getProposedBook().getPublisher(),
                        exchange.getProposedBook().getImageUrl()
                ),

                new BookResponse(
                        exchange.getRequestedBook().getId(),
                        exchange.getRequestedBook().getTitle(),
                        exchange.getRequestedBook().getAuthor(),
                        exchange.getRequestedBook().getPublisher(),
                        exchange.getRequestedBook().getImageUrl()
                ),

                new UserResponse(
                        exchange.getRequesterUser().getId(),
                        exchange.getRequesterUser().getName(),
                        exchange.getRequesterUser().getSurname(),
                        exchange.getRequesterUser().getUsername()
                )
        );
    }
}

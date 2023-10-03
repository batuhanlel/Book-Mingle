package com.lel.bookmingle.controller;

import com.lel.bookmingle.dto.request.ExchangeRequest;
import com.lel.bookmingle.dto.request.book.BookSearchRequest;
import com.lel.bookmingle.dto.response.BookExchangeResponse;
import com.lel.bookmingle.dto.response.ExchangeDemandResponse;
import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.enums.RequestType;
import com.lel.bookmingle.exception.ErrorResponse;
import com.lel.bookmingle.service.BookExchangeService;
import com.lel.bookmingle.service.UserBookExchangeService;
import com.lel.bookmingle.utility.context.ContextProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@AllArgsConstructor
public class ExchangeController {

    private ContextProvider contextProvider;
    private BookExchangeService bookExchangeService;
    private UserBookExchangeService userBookExchangeService;

    @GetMapping("/list/get")
    public ResponseEntity<List<BookExchangeResponse>> getBooks(@ModelAttribute BookSearchRequest request) {
        return ResponseEntity.ok(bookExchangeService.getBookExchangeList(request));
    }

    @PostMapping("/request")
    public ResponseEntity<?> create(@RequestBody ExchangeRequest request) {
        request.setRequesterUserId(contextProvider.get().getUser().getId());
        request.setRequestType(RequestType.EXCHANGE);
        request.setRequestStatus(RequestStatus.PENDING);
        return ResponseEntity.ok(userBookExchangeService.createExchangeRequest(request));
    }

    @GetMapping("/demand/get")
    public ResponseEntity<List<ExchangeDemandResponse>> requestList(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(userBookExchangeService.getExchangeDemandList(page));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<BookExchangeResponse>> getRecommendations(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
        return ResponseEntity.ok(userBookExchangeService.getBookRecommendations(latitude, longitude));
    }

    @GetMapping("/demand/count/get")
    public ResponseEntity<?> requestCount() {
        return ResponseEntity.ok(5);
    }

    @PutMapping(value = "/demand/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateExchangeDemand(@PathVariable("id") Integer id, @RequestBody Boolean isAccepted) {
        try {
            bookExchangeService.updateExchangeDemand(id, isAccepted);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse("Message", e.getMessage()));
        }
    }
}

package com.lel.bookmingle.model;

import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.enums.RequestType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
        name = "book_requests"
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookExchange implements Serializable {
    @Serial
    private static final long serialVersionUID = -3318821723741976434L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_user_id")
    private User requesterUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposed_book_id")
    private Book proposedBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_user_id")
    private User requestedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_book_id")
    private Book requestedBook;

    @Column(name = "requested_date")
    private LocalDate requestedDate;

    @Column(name = "request_type")
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "returned_date")
    private LocalDate returnedDate;
}

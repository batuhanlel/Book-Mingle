package com.lel.bookmingle.repository;

import com.lel.bookmingle.enums.RequestStatus;
import com.lel.bookmingle.model.BookExchange;
import com.lel.bookmingle.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRequestRepository extends JpaRepository<BookExchange, Integer> {

    @Query("SELECT COUNT(be) FROM BookExchange be WHERE be.requesterUser.id = :requesterId AND be.requestStatus = 'ACCEPTED'")
    int countAcceptedRequestsByRequesterId(@Param("requesterId") int requesterId);

    @EntityGraph(attributePaths = {"requesterUser", "requestedBook", "requestedUser", "proposedBook"})
    List<BookExchange> findByRequestedUserAndRequestStatus(User requestedUser, RequestStatus requestStatus, Pageable pageable);
}

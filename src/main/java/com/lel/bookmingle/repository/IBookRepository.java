package com.lel.bookmingle.repository;

import com.lel.bookmingle.dto.response.BookExchangeResponse;
import com.lel.bookmingle.dto.response.BookResponse;
import com.lel.bookmingle.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT new com.lel.bookmingle.dto.response.BookExchangeResponse(b.id, b.title, b.author, b.imageUrl, u.id, u.name, u.surname, u.email)"
            + "FROM book b JOIN b.users u "
            + "WHERE lower(b.title) LIKE lower(concat('%', :title, '%')) "
            + "OR lower(b.author) LIKE lower(concat('%', :author, '%')) ")
    List<BookExchangeResponse> findBookExchangeList(Pageable pageable, @Param("title") String title, @Param("author") String author);

    @Query(value = "SELECT new com.lel.bookmingle.dto.response.BookResponse(b.id, b.title, b.author, b.publisher, b.imageUrl)" +
            "FROM book b JOIN b.users u " +
            "WHERE u.id = :userId")
    List<BookResponse> findBooksByUser(@Param("userId") Integer userId);

    String HAVERSINE_DISTANCE_CLAUSE = "(6371 * acos(cos(radians(:latitude)) * cos(radians(u.latitude)) *" +
            " cos(radians(u.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(u.latitude))))";
    @Query(value = "SELECT new com.lel.bookmingle.dto.response.BookExchangeResponse(b.id, b.title, b.author, b.imageUrl, u.id, u.name, u.surname, u.email)"
            + "FROM book b JOIN b.users u "
            + "WHERE b.title in (:bookList) AND u.id != :userId "
            + "AND " + HAVERSINE_DISTANCE_CLAUSE + " < :distance")
    List<BookExchangeResponse> findBookExchangeListForRecommendations(
            @Param("userId") Integer userId,
            @Param("bookList") List<String> bookList,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distance);

    @Query(value = "SELECT new com.lel.bookmingle.dto.response.BookResponse(b.id, b.title, b.author, b.publisher, b.imageUrl) "
            + "FROM book b "
            + "WHERE lower(b.title)  LIKE lower(concat('%', :title, '%')) "
            + "OR lower(b.author)  LIKE lower(concat('%', :author, '%')) "
            + "OR lower(b.publisher)  LIKE lower(concat('%', :publisher, '%')) ")
    List<BookResponse> findAllBySearchText(Pageable pageable, @Param("title") String title, @Param("author") String author, @Param("publisher") String publisher);
}

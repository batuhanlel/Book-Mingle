package com.lel.bookmingle.repository;

import com.lel.bookmingle.model.Chat;
import com.lel.bookmingle.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Long> {

    @EntityGraph(attributePaths = {"user1", "user2"})
    List<Chat> findByUser1OrUser2(User user1, User user2);

    @EntityGraph(attributePaths = {"user1", "user2"})
    @Query("SELECT c FROM Chat c JOIN c.user1 u1 JOIN c.user2 u2 WHERE (u1.id = :user1Id AND u2.id = :user2Id) OR (u1.id = :user2Id AND u2.id = :user1Id)")
    Chat findChatByUsers(@Param("user1Id") Integer user1Id, @Param("user2Id") Integer user2Id);
}

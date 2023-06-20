package com.lel.bookmingle.repository;

import com.lel.bookmingle.model.Chat;
import com.lel.bookmingle.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChat(Pageable pageable, Chat chat);
}

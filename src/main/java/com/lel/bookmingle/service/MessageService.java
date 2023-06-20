package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.mapper.MessageDTOMapper;
import com.lel.bookmingle.dto.response.ChatMessageResponse;
import com.lel.bookmingle.model.Chat;
import com.lel.bookmingle.model.Message;
import com.lel.bookmingle.repository.IMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private IMessageRepository messageRepository;
    private MessageDTOMapper messageDTOMapper;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<ChatMessageResponse> getChatMessages(int page, Chat chat) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("sentAt").descending());
        return messageRepository.findAllByChat(pageable, chat).stream().map(messageDTOMapper).toList();
    }
}

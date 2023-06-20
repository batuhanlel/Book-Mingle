package com.lel.bookmingle.controller;

import com.lel.bookmingle.dto.request.ChatMessageRequest;
import com.lel.bookmingle.dto.response.ChatResponse;
import com.lel.bookmingle.dto.response.ChatMessageResponse;
import com.lel.bookmingle.model.Chat;
import com.lel.bookmingle.model.Message;
import com.lel.bookmingle.service.ChatService;
import com.lel.bookmingle.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;
    private MessageService messageService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/chat/list/user/get")
    public ResponseEntity<List<ChatResponse>> getChats() {
        return ResponseEntity.ok(chatService.getChatList());
    }

    @GetMapping("/chat/messages/get")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@Param("page") int page, @Param("user1Id") int user1Id, @Param("user2Id") int user2Id) {
        Chat chat = chatService.getMessageChat(user1Id, user2Id);
        return ResponseEntity.ok(messageService.getChatMessages(page, chat));
    }

    @MessageMapping("/chat")
    public void sendMessage(@Payload ChatMessageRequest message) {
        Chat chat = chatService.getMessageChat(message);
        Message savedMessage = messageService.saveMessage(Message.builder()
                .chat(chat)
                .sender(message.senderId().equals(chat.getUser1().getId()) ? chat.getUser1() : chat.getUser2())
                .receiver(message.receiverId().equals(chat.getUser1().getId()) ? chat.getUser1() : chat.getUser2())
                .content(message.content())
                .sentAt(message.sentAt())
                .build());

        chat.setLastMessage(savedMessage.getContent());
        chat.setUpdatedAt(savedMessage.getSentAt());
        chatService.updateLastMessage(chat);

        simpMessagingTemplate.convertAndSendToUser(message.receiverId().toString(), "/queue/messages", message);
    }
}

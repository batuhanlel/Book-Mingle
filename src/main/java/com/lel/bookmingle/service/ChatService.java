package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.mapper.ChatDTOMapper;
import com.lel.bookmingle.dto.request.ChatMessageRequest;
import com.lel.bookmingle.dto.response.ChatResponse;
import com.lel.bookmingle.model.Chat;
import com.lel.bookmingle.model.User;
import com.lel.bookmingle.repository.IChatRepository;
import com.lel.bookmingle.utility.context.ContextManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ChatService {

    private IChatRepository chatRepository;
    private ChatDTOMapper chatDTOMapper;

    public List<ChatResponse> getChatList() {
        User user = ContextManager.get().getUser();
        List<Chat> chats =  chatRepository.findByUser1OrUser2(user, user);
        return chats.stream().map(chatDTOMapper).toList();
    }

    public Chat getMessageChat(ChatMessageRequest message) {
        return chatRepository.findChatByUsers(message.senderId(), message.receiverId());
    }

    public Chat getMessageChat(int senderId, int receiverId) {
        return chatRepository.findChatByUsers(senderId, receiverId);
    }

    protected boolean createChat(User user1, User user2) {
        Chat newChat = chatRepository.save(Chat.builder()
                        .user1(user1)
                        .user2(user2)
                        .createdAt(LocalDateTime.now())
                .build());
        return Objects.nonNull(newChat.getChatId());
    }

    public void updateLastMessage(Chat chat) {
        chatRepository.save(chat);
    }
}

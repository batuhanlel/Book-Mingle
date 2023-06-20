package com.lel.bookmingle.dto.mapper;

import com.lel.bookmingle.dto.response.ChatResponse;
import com.lel.bookmingle.dto.response.UserResponse;
import com.lel.bookmingle.model.Chat;
import com.lel.bookmingle.utility.context.ContextManager;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ChatDTOMapper implements Function<Chat, ChatResponse> {
    @Override
    public ChatResponse apply(Chat chat) {
        Integer senderId = ContextManager.get().getUser().getId();
        return new ChatResponse(
                chat.getChatId(),
                senderId.equals(chat.getUser1().getId()) ?
                        new UserResponse(
                                chat.getUser2().getId(),
                                chat.getUser2().getName(),
                                chat.getUser2().getSurname(),
                                chat.getUser2().getEmail()
                        ) :
                        new UserResponse(
                                chat.getUser1().getId(),
                                chat.getUser1().getName(),
                                chat.getUser1().getSurname(),
                                chat.getUser1().getEmail()
                        ),
                chat.getLastMessage(),
                chat.getUpdatedAt()
        );
    }
}

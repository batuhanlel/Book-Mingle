package com.lel.bookmingle.dto.mapper;

import com.lel.bookmingle.dto.response.ChatMessageResponse;
import com.lel.bookmingle.model.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MessageDTOMapper implements Function<Message, ChatMessageResponse> {

    @Override
    public ChatMessageResponse apply(Message message) {
        return new ChatMessageResponse(
                message.getSender().getId(),
                message.getReceiver().getId(),
                message.getContent()
        );
    }
}

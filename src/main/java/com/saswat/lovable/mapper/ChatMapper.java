package com.saswat.lovable.mapper;

import com.saswat.lovable.dto.chat.ChatResponse;
import com.saswat.lovable.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> fromListOfChatMessage(List<ChatMessage> chatMessageList);
}

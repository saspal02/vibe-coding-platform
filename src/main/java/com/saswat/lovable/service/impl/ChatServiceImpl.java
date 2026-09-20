package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.chat.ChatResponse;
import com.saswat.lovable.entity.ChatMessage;
import com.saswat.lovable.entity.ChatSession;
import com.saswat.lovable.entity.ChatSessionId;
import com.saswat.lovable.mapper.ChatMapper;
import com.saswat.lovable.repository.ChatMessageRepository;
import com.saswat.lovable.repository.ChatSessionRepository;
import com.saswat.lovable.security.AuthUtil;
import com.saswat.lovable.security.UserContext;
import com.saswat.lovable.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final UserContext userContext;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        ChatSession chatSession = chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId, userContext.getUserId())
        );

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.fromListOfChatMessage(chatMessageList);
    }
}

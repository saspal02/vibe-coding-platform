package com.saswat.lovable.dto.chat;

import com.saswat.lovable.entity.ChatEvent;
import com.saswat.lovable.entity.ChatSession;
import com.saswat.lovable.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        ChatSession chatSession,
        MessageRole role,
        List<ChatEvent> events,
        String content,
        Integer tokensUsed,
        Instant createdAt
) {


}

package com.saswat.lovable.dto.chat;

import java.util.List;

public record ChatEventResponse(
        Long id,
        List<ChatEventResponse> events,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}

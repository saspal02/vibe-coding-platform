package com.saswat.lovable.service;

import com.saswat.lovable.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}

package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.chat.StreamResponse;
import com.saswat.lovable.entity.*;
import com.saswat.lovable.enums.ChatEventType;
import com.saswat.lovable.enums.MessageRole;
import com.saswat.lovable.exception.ResourceNotFoundException;
import com.saswat.lovable.llm.LlmResponseParser;
import com.saswat.lovable.llm.PromptUtils;
import com.saswat.lovable.llm.advisors.FileTreeContextAdvisor;
import com.saswat.lovable.repository.*;
import com.saswat.lovable.security.AuthUtil;
import com.saswat.lovable.service.AiGenerationService;
import com.saswat.lovable.service.ProjectFileService;
import com.saswat.lovable.service.UsageService;
import com.saswat.lovable.tools.CodeGenerationTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;
    private final LlmResponseParser llmResponseParser;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatEventRepository chatEventRepository;
    private final UsageService usageService;

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<StreamResponse> streamResponse(String userMessage, Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        ChatSession chatSession = createChatSessionIfNotExists(projectId, userId);

        Map<String, Object> advisorParams = Map.of(
                "userId", authUtil.getCurrentUserId(),
                "projectId", projectId
        );

        StringBuilder fullResponseBuffer = new StringBuilder();

        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(projectFileService, projectId);

        AtomicReference<Long> startTime = new AtomicReference<>(System.currentTimeMillis());
        AtomicReference<Long> endTime = new AtomicReference<>(0L);
        AtomicReference<Usage> usageRef = new AtomicReference<>();

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(userMessage)
                .tools(codeGenerationTools)
                .advisors(advisorSpec -> {
                            advisorSpec.params(advisorParams);
                            advisorSpec.advisors(fileTreeContextAdvisor);
                        }

                )
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    String content = response.getResult().getOutput().getText();

                    if (content != null & !content.isEmpty() && endTime.get() == 0) { // first non-empty chunk received
                        endTime.set(System.currentTimeMillis());
                    }
                    if (response.getMetadata().getUsage() != null) {
                        usageRef.set(response.getMetadata().getUsage());
                    }
                    fullResponseBuffer.append(content);

                })
                .doOnComplete(() -> {
                    Schedulers.boundedElastic().schedule(() -> {
//                        parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                        long duration = (endTime.get() - startTime.get()) / 1000;
                        finalizeChats(userMessage, chatSession, fullResponseBuffer.toString(), duration, usageRef.get());
                    });

                })
                .doOnError(error -> log.error("Error during streaming for projectId {}: ", projectId))
                .map(response -> {
                    String text = response.getResult().getOutput().getText();
                    return new StreamResponse(text != null ? text : "");
                });

    }
    
    private void finalizeChats(String userMessage, ChatSession chatSession, String fullText, long duration, Usage usage) {
        Long projectId = chatSession.getProject().getId();

        if (usage != null) {
            int totalTokens = usage.getTotalTokens();
            usageService.recordTokenUsage(chatSession.getUser().getId(), totalTokens);
        }
        // Save the user message
        chatMessageRepository.save(
                ChatMessage.builder()
                        .chatSession(chatSession)
                        .role(MessageRole.USER)
                        .content(userMessage)
                        .tokensUsed(usage.getPromptTokens())
                        .build()
        );
        
        ChatMessage assistantChatMessage = ChatMessage.builder()
                .role(MessageRole.ASSISTANT)
                .content("Assistant Message here...")
                .chatSession(chatSession)
                .tokensUsed(usage.getCompletionTokens())
                .build();
        
        assistantChatMessage = chatMessageRepository.save(assistantChatMessage);
        
        List<ChatEvent> chatEventList = llmResponseParser.parseChatEvents(fullText, assistantChatMessage);
        chatEventList.addFirst(ChatEvent.builder()
                .type(ChatEventType.THOUGHT)
                .chatMessage(assistantChatMessage)
                .content("Thought for "+duration+"s")
                .sequenceOrder(0)
                .build());
        chatEventList.stream()
                .filter(e -> e.getType() == ChatEventType.FILE_EDIT)
                .forEach(e -> projectFileService.saveFile(projectId, e.getFilePath(), e.getContent()));

        chatEventRepository.saveAll(chatEventList);
    }


    private ChatSession createChatSessionIfNotExists(Long projectId, Long userId) {
        ChatSessionId chatSessionId = new ChatSessionId(projectId, userId);
        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElse(null);


        if (chatSession == null) {
            Project project = projectRepository.findById(projectId).
                    orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

            chatSession = ChatSession.builder()
                    .id(chatSessionId)
                    .project(project)
                    .user(user)
                    .build();
            
            chatSession = chatSessionRepository.save(chatSession);
        }
        
        return chatSession;


    }
}

package com.saswat.lovable.entity;

import com.saswat.lovable.common.entity.BaseEntity;
import com.saswat.lovable.enums.MessageRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage extends BaseEntity {

    private Long id;
    private ChatSession chatSession;
    private String content;
    private MessageRole role;
    private String toolCalls;
    private Integer tokensUsed;
    private Integer createdAt;

}

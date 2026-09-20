package com.saswat.lovable.entity;

import com.saswat.lovable.enums.ChatEventType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id", nullable = false)
    private ChatMessage chatMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatEventType type;

    @Column(nullable = false)
    private Integer sequenceOrder;

    @Column(columnDefinition = "text")
    private String content;

    private String filePath;

    @Column(columnDefinition = "text")
    private String metadata;

}

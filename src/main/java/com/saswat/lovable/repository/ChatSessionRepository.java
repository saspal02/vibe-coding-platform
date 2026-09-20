package com.saswat.lovable.repository;

import com.saswat.lovable.entity.ChatSession;
import com.saswat.lovable.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}

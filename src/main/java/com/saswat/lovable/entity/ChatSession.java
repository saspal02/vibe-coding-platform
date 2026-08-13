package com.saswat.lovable.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.Instant;

@Getter
@Setter
public class ChatSession {

    private Project project;
    private User user;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}

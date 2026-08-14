package com.saswat.lovable.entity;

import com.saswat.lovable.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.Instant;

@Getter
@Setter
public class ChatSession extends BaseEntity {

    private Project project;
    private User user;
    private String title;
    private Instant deletedAt;
}

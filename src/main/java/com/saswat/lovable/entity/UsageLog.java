package com.saswat.lovable.entity;

import java.time.Instant;

public class UsageLog {

    private Long id;
    private User user;
    private Project project;
    private String action;
    private Integer tokensUsed;
    private Integer durationMs;
    private String metaData;
    private Instant createdAt;

}

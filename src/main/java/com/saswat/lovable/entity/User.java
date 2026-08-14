package com.saswat.lovable.entity;

import com.saswat.lovable.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class User extends BaseEntity {

    private Long id;
    private String email;
    private String passwordHash;
    private String name;
    private String avatarUrl;
    private Instant createdAt;

}

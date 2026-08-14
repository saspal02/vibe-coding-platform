package com.saswat.lovable.entity;

import com.saswat.lovable.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.Instant;

@Getter
@Setter
public class Project extends BaseEntity {

    private Long id;
    private String name;
    private User owner;
    private Boolean isPublic = false;
    private Instant deletedAt;
}

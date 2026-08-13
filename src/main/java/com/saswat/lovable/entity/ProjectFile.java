package com.saswat.lovable.entity;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.Instant;

@Getter
@Setter
public class ProjectFile {

    private Long id;
    private Project project;
    private String path;
    private String minioObjectKey;
    private Instant createdAt;
    private Instant updatedAt;
    private User createdBy;
    private User updatedBy;
}

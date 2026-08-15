package com.saswat.lovable.entity;

import com.saswat.lovable.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectFile extends BaseEntity {

    private Long id;
    private Project project;
    private String path;
    private String minioObjectKey;
    private User createdBy;
    private User updatedBy;
}

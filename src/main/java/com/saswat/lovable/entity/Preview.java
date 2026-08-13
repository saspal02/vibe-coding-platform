package com.saswat.lovable.entity;

import com.saswat.lovable.enums.PreviewStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Preview {

    private Long id;
    private Project project;
    private String namespace;
    private String podName;
    private String previewUrl;
    private PreviewStatus status;
    private Instant startedAt;
    private Instant terminatedAt;
    private Instant createdAt;
}

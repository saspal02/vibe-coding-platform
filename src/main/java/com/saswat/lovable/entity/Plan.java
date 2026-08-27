package com.saswat.lovable.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String stripePriceId;
    private Integer maxProjects;
    private Integer maxTokensPerDay;
    private Integer maxPreviews;
    private Boolean unlimitedAi;
    private Boolean active;
}

package com.saswat.lovable.entity;


import com.saswat.lovable.common.entity.BaseEntity;
import com.saswat.lovable.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "plan_id")
    private Plan plan;

    @Enumerated(value = EnumType.STRING)
    private SubscriptionStatus status;

    private String stripeSubscriptionId; // can be renamed to gatewaySubscriptionId

    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;
    private Boolean cancelAtPeriodEnd = false;


}

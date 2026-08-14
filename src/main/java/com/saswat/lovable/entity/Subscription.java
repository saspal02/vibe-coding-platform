package com.saswat.lovable.entity;


import com.saswat.lovable.common.entity.BaseEntity;
import com.saswat.lovable.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;

import java.time.Instant;

@Getter
@Setter
public class Subscription extends BaseEntity {

    private Long id;

    private User user;
    private Plan plan;

    private SubscriptionStatus status;

    private String stripeCustomerId;
    private String stripeSubscriptionId;

    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;
    private Boolean cancelAtPeriodEnd = false;


}

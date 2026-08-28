package com.saswat.lovable.service;

import com.saswat.lovable.dto.subscriiption.SubscriptionResponse;
import com.saswat.lovable.enums.SubscriptionStatus;

import java.time.Instant;

public interface SubscriptionService {

    SubscriptionResponse getCurrentSubscription();

    void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId);

    void updateSubscription(String subscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd,
                            Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String subscriptionId);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);


    void markSubscriptionPastDue(String subId);

    boolean canCreateNewProject();
}

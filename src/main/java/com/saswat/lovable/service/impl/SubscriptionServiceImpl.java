package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.subscriiption.SubscriptionResponse;
import com.saswat.lovable.entity.Plan;
import com.saswat.lovable.entity.Subscription;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.enums.SubscriptionStatus;
import com.saswat.lovable.exception.ResourceNotFoundException;
import com.saswat.lovable.mapper.SubscriptionMapper;
import com.saswat.lovable.repository.PlanRepository;
import com.saswat.lovable.repository.SubscriptionRepository;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.security.UserContext;
import com.saswat.lovable.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserContext userContext;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;


    @Override
    public SubscriptionResponse getCurrentSubscription() {
        var currentSubscription = subscriptionRepository.findByUserIdAndStatusIn(userContext.getUserId(), Set.of(
                SubscriptionStatus.ACTIVE, SubscriptionStatus.PAST_DUE,
                SubscriptionStatus.TRIALING
        )).orElse(
                new Subscription()
        );

        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        boolean exists = subscriptionRepository.existsByStripeSubscription(subscriptionId);
        if (exists) return;

        User user = getUser(userId);
        Plan plan = getPlan(planId);

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);

    }

    @Override
    public void updateSubscription(String subscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {

    }

    @Override
    public void cancelSubscription(String subscriptionId) {

    }

    @Override
    public void renewSubscriptionPeriod(String gatewaySubscriptionId, Instant periodStart, Instant periodEnd) {

        Subscription subscription = getSubscription(gatewaySubscriptionId);

        Instant newStart = periodStart != null ? periodStart : subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if (subscription.getStatus() == SubscriptionStatus.PAST_DUE || subscription.getStatus() == SubscriptionStatus.INCOMPLETE) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        subscriptionRepository.save(subscription);

    }


    @Override
    public void markSubscriptionPastDue(String gatewaySubscriptionId) {
       Subscription subscription = getSubscription(gatewaySubscriptionId);

       if (subscription.getStatus() == SubscriptionStatus.PAST_DUE) {
           log.debug("Subscription is already past due, gatewaySubscriptionId: {}", gatewaySubscriptionId);
           return;
       }

       subscription.setStatus(SubscriptionStatus.PAST_DUE);
       subscriptionRepository.save(subscription);

    }

    //Utility methods

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", planId.toString()));
    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscription(gatewaySubscriptionId).orElseThrow(() ->
                new ResourceNotFoundException("subscription", gatewaySubscriptionId));
    }
}

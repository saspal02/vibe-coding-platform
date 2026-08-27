package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.subscriiption.CheckoutRequest;
import com.saswat.lovable.dto.subscriiption.CheckoutResponse;
import com.saswat.lovable.dto.subscriiption.PortalResponse;
import com.saswat.lovable.entity.Plan;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.exception.ResourceNotFoundException;
import com.saswat.lovable.repository.PlanRepository;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.security.UserContext;
import com.saswat.lovable.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePaymentProcessorImpl implements PaymentProcessor {

    private final UserContext userContext;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Value("${client.url}")
    private String frontendUrl;


    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Plan plan = planRepository.findById(request.planId()).orElseThrow(() ->
                new ResourceNotFoundException("Plan", request.planId().toString()));

        User user = userRepository.findById(userContext.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("Plan", request.planId().toString()));

        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                .putMetadata("user_id", userContext.getUserId().toString())
                .putMetadata("plan_id", plan.getId().toString());


        try {
            String stripeCustomerId = user.getStripeCustomerId();
            if (stripeCustomerId == null || stripeCustomerId.isEmpty()) {
                params.setCustomerEmail(user.getUsername());
            } else {
                params.setCustomer(stripeCustomerId);// stripe customer id
            }

            Session session = Session.create(params.build()); // making api call to the stripe Backend
            return new CheckoutResponse(session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }

    @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {
        log.info("type");

    }
}

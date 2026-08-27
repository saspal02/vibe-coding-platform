package com.saswat.lovable.service;

import com.saswat.lovable.dto.subscriiption.CheckoutRequest;
import com.saswat.lovable.dto.subscriiption.CheckoutResponse;
import com.saswat.lovable.dto.subscriiption.PortalResponse;
import com.stripe.model.StripeObject;

import java.util.Map;

public interface PaymentProcessor {

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal();

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);

}

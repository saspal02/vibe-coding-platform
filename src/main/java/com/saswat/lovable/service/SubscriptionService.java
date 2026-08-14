package com.saswat.lovable.service;

import com.saswat.lovable.dto.subscriiption.CheckoutRequest;
import com.saswat.lovable.dto.subscriiption.CheckoutResponse;
import com.saswat.lovable.dto.subscriiption.PortalResponse;
import com.saswat.lovable.dto.subscriiption.SubscriptionResponse;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);

}

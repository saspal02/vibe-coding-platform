package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.subscriiption.CheckoutRequest;
import com.saswat.lovable.dto.subscriiption.CheckoutResponse;
import com.saswat.lovable.dto.subscriiption.PortalResponse;
import com.saswat.lovable.dto.subscriiption.SubscriptionResponse;
import com.saswat.lovable.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}

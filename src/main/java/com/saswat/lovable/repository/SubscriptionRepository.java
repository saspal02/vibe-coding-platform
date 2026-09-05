package com.saswat.lovable.repository;

import com.saswat.lovable.entity.Subscription;
import com.saswat.lovable.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> active);

    Optional<Subscription> findByStripeSubscriptionId(String subscriptionId);

    boolean existsByStripeSubscriptionId(String gatewaySubscriptionId);

}

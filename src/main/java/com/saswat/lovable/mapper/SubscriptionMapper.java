package com.saswat.lovable.mapper;

import com.saswat.lovable.dto.subscriiption.PlanResponse;
import com.saswat.lovable.dto.subscriiption.SubscriptionResponse;
import com.saswat.lovable.entity.Plan;
import com.saswat.lovable.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}

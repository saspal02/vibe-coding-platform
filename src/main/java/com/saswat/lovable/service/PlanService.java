package com.saswat.lovable.service;

import com.saswat.lovable.dto.subscription.PlanResponse;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();

}

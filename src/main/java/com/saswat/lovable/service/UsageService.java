package com.saswat.lovable.service;

import com.saswat.lovable.dto.subscriiption.PlanLimitsResponse;
import com.saswat.lovable.dto.subscriiption.UsageTodayResponse;

public interface UsageService {

    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getPlanLimitsOfUser(Long userId);
}

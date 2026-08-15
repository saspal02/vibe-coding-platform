package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.subscriiption.PlanLimitsResponse;
import com.saswat.lovable.dto.subscriiption.UsageTodayResponse;
import com.saswat.lovable.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {

    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getPlanLimitsOfUser(Long userId) {
        return null;
    }
}

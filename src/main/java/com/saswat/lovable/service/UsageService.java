package com.saswat.lovable.service;

import com.saswat.lovable.dto.subscriiption.PlanLimitsResponse;
import com.saswat.lovable.dto.subscriiption.UsageTodayResponse;

public interface UsageService {

   void recordTokenUsage(Long userId, int actualTokens);
   void checkDailyTokenUsage();
}

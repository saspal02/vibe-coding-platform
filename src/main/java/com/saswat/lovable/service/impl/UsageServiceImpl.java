package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.subscription.PlanResponse;
import com.saswat.lovable.dto.subscription.SubscriptionResponse;
import com.saswat.lovable.entity.UsageLog;
import com.saswat.lovable.repository.UsageLogRepository;
import com.saswat.lovable.security.AuthUtil;
import com.saswat.lovable.service.SubscriptionService;
import com.saswat.lovable.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UsageServiceImpl implements UsageService {
    
    private final UsageLogRepository usageLogRepository;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;

    @Override
    public void recordTokenUsage(Long userId, int actualTokens) {
        LocalDate today = LocalDate.now();
        
        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> createNewDailyLog(userId, today));
        
        todayLog.setTokensUsed(todayLog.getTokensUsed() + actualTokens);
        usageLogRepository.save(todayLog);

    }

    @Override
    public void checkDailyTokenUsage() {
        Long userId = authUtil.getCurrentUserId();
        SubscriptionResponse subscriptionResponse = subscriptionService.getCurrentSubscription();
        PlanResponse plan = subscriptionResponse.plan();

        LocalDate today = LocalDate.now();

        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> createNewDailyLog(userId, today));

        if (plan.unlimitedAi()) return;

        int currentUsage = todayLog.getTokensUsed();
        int limit = plan.maxTokensPerDay();

        if (currentUsage >= limit) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Daily limit reached, Upgrade now");
        }

    }

    private UsageLog createNewDailyLog(Long userId, LocalDate date) {
        UsageLog newLog = UsageLog.builder()
                .userId(userId)
                .date(date)
                .tokensUsed(0)
                .build();
        return usageLogRepository.save(newLog);
    }
}

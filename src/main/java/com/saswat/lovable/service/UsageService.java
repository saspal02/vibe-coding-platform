package com.saswat.lovable.service;

public interface UsageService {

   void recordTokenUsage(Long userId, int actualTokens);
   void checkDailyTokenUsage();
}

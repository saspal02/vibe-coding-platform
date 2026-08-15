package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.subscriiption.PlanResponse;
import com.saswat.lovable.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}

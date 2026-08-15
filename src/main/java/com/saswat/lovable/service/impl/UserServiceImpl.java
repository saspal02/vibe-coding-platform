package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.auth.UserProfileResponse;
import com.saswat.lovable.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}

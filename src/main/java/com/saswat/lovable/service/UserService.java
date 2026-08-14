package com.saswat.lovable.service;

import com.saswat.lovable.dto.auth.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}

package com.saswat.lovable.service;

import com.saswat.lovable.dto.auth.AuthResponse;
import com.saswat.lovable.dto.auth.LoginRequest;
import com.saswat.lovable.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse signup(SignupRequest request);
}

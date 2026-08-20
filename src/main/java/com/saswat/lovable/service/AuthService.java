package com.saswat.lovable.service;

import com.saswat.lovable.dto.auth.AuthResponse;
import com.saswat.lovable.dto.auth.LoginRequest;
import com.saswat.lovable.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);


}

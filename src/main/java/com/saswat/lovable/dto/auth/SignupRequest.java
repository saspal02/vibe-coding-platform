package com.saswat.lovable.dto.auth;

public record SignupRequest(
        String email,
        String name,
        String password
) {
}

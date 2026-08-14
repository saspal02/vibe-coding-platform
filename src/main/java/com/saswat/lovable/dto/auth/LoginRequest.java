package com.saswat.lovable.dto.auth;

public record LoginRequest(
        String email,
        String password
) {
}

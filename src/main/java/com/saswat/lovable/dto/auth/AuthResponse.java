package com.saswat.lovable.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user) {

}

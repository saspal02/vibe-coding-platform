package com.saswat.lovable.dto.project;

import com.saswat.lovable.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        UserProfileResponse owner
) {
}

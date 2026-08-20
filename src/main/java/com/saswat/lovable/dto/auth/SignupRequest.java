package com.saswat.lovable.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Email @NotBlank String username,
        @Size(min = 1, max = 30) String name,
        @Size(min = 6, max = 100) String password
) {
}

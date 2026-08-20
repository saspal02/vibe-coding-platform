package com.saswat.lovable.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Email String username,
        @Size(min = 6, max = 100) String password
) {
}

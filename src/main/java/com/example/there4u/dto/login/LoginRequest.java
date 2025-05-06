package com.example.there4u.dto.login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        String username,
        String email,

        @NotBlank
        String password
) {}

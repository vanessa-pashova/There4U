package com.example.there4u.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponse(
        @NotBlank
        String userType,

        @NotNull
        Long userId,

        @NotBlank
        String username
) {}

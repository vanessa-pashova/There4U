package com.example.there4u.dto.NGOUser;

import jakarta.validation.constraints.NotBlank;

public record NGOUserRegisterRequest(
        @NotBlank
        String username,

        @NotBlank
        String name,

        @NotBlank
        String address,

        @NotBlank
        String phone,

        @NotBlank
        String email,

        @NotBlank
        String password,

        String description
) {}

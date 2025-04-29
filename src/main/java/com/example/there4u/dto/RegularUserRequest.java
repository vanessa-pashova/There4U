package com.example.there4u.dto;

import jakarta.validation.constraints.NotBlank;

public record RegularUserRequest(
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

        @NotBlank
        String ucn
) {}

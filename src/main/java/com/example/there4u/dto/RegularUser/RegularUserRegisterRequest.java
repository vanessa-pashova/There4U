package com.example.there4u.dto.RegularUser;

import jakarta.validation.constraints.NotBlank;

public record RegularUserRegisterRequest(
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

package com.example.there4u.dto.Contributor;

import jakarta.validation.constraints.NotBlank;

public record ContributorRegisterRequest(
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
        String typeOfUser,

        String description
) {}

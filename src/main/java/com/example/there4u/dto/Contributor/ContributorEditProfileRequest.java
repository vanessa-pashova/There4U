package com.example.there4u.dto.Contributor;

public record ContributorEditProfileRequest(
        String username,

        String name,

        String password,

        String phoneNumber,

        String address,

        String typeOfUser,

        String description
) {}

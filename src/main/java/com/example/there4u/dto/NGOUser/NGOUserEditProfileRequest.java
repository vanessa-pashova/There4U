package com.example.there4u.dto.NGOUser;

public record NGOUserEditProfileRequest(
        String username,

        String name,

        String password,

        String phoneNumber,

        String address,

        String description
) {}

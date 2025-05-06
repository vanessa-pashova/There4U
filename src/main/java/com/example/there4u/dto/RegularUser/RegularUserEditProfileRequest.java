package com.example.there4u.dto.RegularUser;

public record RegularUserEditProfileRequest(
        String username,

        String name,

        String password,

        String phoneNumber,

        String address
) {}

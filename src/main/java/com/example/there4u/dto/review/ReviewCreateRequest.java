package com.example.there4u.dto.review;

import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest (
        @NotNull
        long id,

        String comment,
        Integer rating,

        @NotNull
        UserDto userDto
){}

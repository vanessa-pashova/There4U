package com.example.there4u.dto.review;

public record ReviewUpdateRequest (
        String comment,
        Integer rating
) {}

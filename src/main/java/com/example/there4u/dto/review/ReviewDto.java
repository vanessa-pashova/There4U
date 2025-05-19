package com.example.there4u.dto.review;

import com.example.there4u.model.review.Review;

public record ReviewDto(
        Long id,
        String comment,
        Integer rating,
        UserDto user
) {
        public static ReviewDto fromEntity(Review review) {
                return new ReviewDto(
                        review.getId(),
                        review.getComment(),
                        review.getRating(),
                        UserDto.fromEntity(review.getUser())
                );
        }
}

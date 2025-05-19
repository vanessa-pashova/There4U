package com.example.there4u.service.review;

import com.example.there4u.dto.review.ReviewCreateRequest;
import com.example.there4u.dto.review.ReviewDto;
import com.example.there4u.model.review.Review;
import com.example.there4u.dto.review.ReviewUpdateRequest;
import com.example.there4u.model.user.User;
import com.example.there4u.repository.general_users.UserRepository;
import com.example.there4u.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return ReviewDto.fromEntity(review);
    }

    public ReviewDto createReview(ReviewCreateRequest reviewCreateRequest) {
        User user = userRepository.findById(reviewCreateRequest.userDto().id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();

        if (reviewCreateRequest.comment() != null) {
            review.setComment(reviewCreateRequest.comment());
        }

        if (reviewCreateRequest.rating() != null) {
            review.setRating(reviewCreateRequest.rating());
        }

        review.setUser(user);
        Review saved = reviewRepository.save(review);

        return ReviewDto.fromEntity(saved);
    }

    public ReviewDto updateReview(Long id, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if (request.comment() == null && request.rating() == null) {
            reviewRepository.delete(review);
            log.info("Review with id {} deleted due to empty update request.", id);
            return null;
        }

        if (request.comment() != null) {
            review.setComment(request.comment());
        }

        if (request.rating() != null) {
            review.setRating(request.rating());
        }

        Review updated = reviewRepository.save(review);
        log.info("Review updated with id: {}", id);
        return ReviewDto.fromEntity(updated);
    }


    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        reviewRepository.delete(review);
        log.info("Review deleted by user with id: {}", id);
    }
}

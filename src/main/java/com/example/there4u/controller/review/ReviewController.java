package com.example.there4u.controller.review;

import com.example.there4u.dto.review.ReviewCreateRequest;
import com.example.there4u.dto.review.ReviewDto;
import com.example.there4u.dto.review.ReviewUpdateRequest;
import com.example.there4u.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        ReviewDto review = reviewService.getReviewById(id);
        return (review != null) ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateRequest review) {
        reviewService.createReview(review);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequest request) {
        ReviewDto updated = reviewService.updateReview(id, request);

        if (updated == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}

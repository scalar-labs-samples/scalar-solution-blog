package com.review.model.dto.response;

import com.review.model.entity.Review;

public record ReviewResponse(
    String id,
    String productId,
    String userId,
    int star,
    String comment
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
            review.id(),
            review.productId(),
            review.userId(),
            review.star(),
            review.comment()
        );
    }
}

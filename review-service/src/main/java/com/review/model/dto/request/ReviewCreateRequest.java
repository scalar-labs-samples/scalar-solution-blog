package com.review.model.dto.request;

public record ReviewCreateRequest(
    String productId,
    String userId,
    int star,
    String comment
) {
}

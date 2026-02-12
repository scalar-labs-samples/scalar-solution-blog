package com.review.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("reviews")
public record Review(
    @Id
    String id,
    String productId,
    String userId,
    int star,
    String comment
) {
}

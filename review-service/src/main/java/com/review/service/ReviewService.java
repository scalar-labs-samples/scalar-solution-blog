package com.review.service;

import com.github.f4b6a3.uuid.UuidCreator;
import com.review.model.dto.request.ReviewCreateRequest;
import com.review.model.entity.Review;
import com.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return StreamSupport.stream(reviewRepository.findAll().spliterator(), false)
            .toList();
    }

    public Review create(ReviewCreateRequest request) {
        var id = UuidCreator.getTimeOrderedEpoch().toString();
        var review = new Review(
            id,
            request.productId(),
            request.userId(),
            request.star(),
            request.comment()
        );
        reviewRepository.insert(review);
        return review;
    }
}

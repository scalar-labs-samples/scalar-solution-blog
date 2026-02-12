package com.review.controller;

import com.review.model.dto.request.ReviewCreateRequest;
import com.review.model.dto.response.ReviewResponse;
import com.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAll() {
        var reviews = reviewService.findAll().stream()
            .map(ReviewResponse::from)
            .toList();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ReviewCreateRequest request) {
        reviewService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("200 success!!");
    }
}

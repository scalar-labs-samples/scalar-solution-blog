package com.review.repository;

import com.review.model.entity.Review;
import com.scalar.db.sql.springdata.ScalarDbRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ReviewRepository extends ScalarDbRepository<Review, String> {
}

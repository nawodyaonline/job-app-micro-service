package com.incognito.review_service.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    void addReview(Review review, Long companyId);
    Review getReview(Long reviewId);
    boolean updateReview(Review review, Long reviewId);
    boolean deleteReview(Long reviewId);
}

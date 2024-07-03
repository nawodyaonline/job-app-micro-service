package com.incognito.review_service.review.impl;


import com.incognito.review_service.review.Review;
import com.incognito.review_service.review.ReviewRepo;
import com.incognito.review_service.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepo reviewRepo;

    public ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepo.findByCompanyId(companyId);
    }

    @Override
    public void addReview(Review review, Long companyId) {
        if (companyId == null && review != null) {
            throw new RuntimeException("Company not found");
        } else {
            review.setCompanyId(companyId);
            reviewRepo.save(review);
        }
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepo.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReview(Review updatedReview, Long reviewId) {
        Review review = reviewRepo.findById(reviewId).orElse(null);
        if(review != null) {
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepo.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepo.findById(reviewId).orElse(null);
        if(review != null) {
            reviewRepo.delete(review);
            return true;
        }
        return false;
    }


}

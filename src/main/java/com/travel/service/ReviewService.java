package com.travel.service;

import java.util.List;

import com.travel.dto.ReviewDTO;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long id);

    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);

    String deleteReview(Long id);
    
    List<ReviewDTO> getReviewsByPackage(Long packageId);

    List<ReviewDTO> getReviewsByUser(Long userId);

    Double getAverageRating(Long packageId);

}
package com.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.dto.ReviewDTO;
import com.travel.entity.Review;
import com.travel.entity.TourPackage;
import com.travel.entity.User;
import com.travel.repository.ReviewRepository;
import com.travel.repository.TourPackageRepository;
import com.travel.repository.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TourPackageRepository tourPackageRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserRepository userRepository,
                             TourPackageRepository tourPackageRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
    	
    	if (reviewRepository.existsByUserIdAndTourPackageId(
    	        reviewDTO.getUserId(),
    	        reviewDTO.getPackageId())) {

    	    throw new RuntimeException("You have already reviewed this package.");
    	}

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TourPackage tourPackage = tourPackageRepository.findById(reviewDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setUser(user);
        review.setTourPackage(tourPackage);

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return mapToDTO(review);
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TourPackage tourPackage = tourPackageRepository.findById(reviewDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setUser(user);
        review.setTourPackage(tourPackage);

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDTO> getReviewsByPackage(Long packageId) {

        return reviewRepository.findByTourPackageId(packageId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    @Override
    public String deleteReview(Long id) {

        reviewRepository.deleteById(id);
        return "Review deleted successfully";
    }

    private ReviewDTO mapToDTO(Review review) {

        return new ReviewDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getId(),
                review.getTourPackage().getId());
    }
    
    @Override
    public List<ReviewDTO> getReviewsByUser(Long userId) {

        return reviewRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    @Override
    public Double getAverageRating(Long packageId) {

        Double average = reviewRepository.getAverageRating(packageId);

        return average != null ? average : 0.0;
    }
}
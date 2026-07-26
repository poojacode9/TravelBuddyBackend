package com.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travel.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByTourPackageId(Long packageId);

    List<Review> findByUserId(Long userId);

    boolean existsByUserIdAndTourPackageId(Long userId, Long packageId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tourPackage.id = :packageId")
    Double getAverageRating(Long packageId);
    
}
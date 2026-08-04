package com.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByTourPackageGuideId(Long guideId);

    List<Booking> findByTourPackageId(Long packageId);
    
    List<Booking> findByUserEmail(String email);
    
    
}
package com.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.TourPackage;
import com.travel.enums.PackageStatus;

@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {
	
	List<TourPackage> findByStatus(PackageStatus status);

	List<TourPackage> findByGuideId(Long guideId);
	
	List<TourPackage> findByDestinationContainingIgnoreCase(String destination);

}
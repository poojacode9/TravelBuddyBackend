package com.travel.service;

import java.util.List;

import com.travel.dto.TourPackageDTO;

public interface TourPackageService {

	TourPackageDTO createPackage(TourPackageDTO dto, String email);

    List<TourPackageDTO> getAllPackages();

    TourPackageDTO getPackageById(Long id);

    TourPackageDTO updatePackage(Long id, TourPackageDTO tourPackageDTO);

    String deletePackage(Long id);
    
    TourPackageDTO approvePackage(Long id);

    TourPackageDTO rejectPackage(Long id);

    List<TourPackageDTO> getPendingPackages();
    
    List<TourPackageDTO> getApprovedPackages();

    List<TourPackageDTO> getPackagesByGuide(Long guideId);
    
    
}
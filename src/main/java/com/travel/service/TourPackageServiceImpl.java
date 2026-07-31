package com.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.dto.TourPackageDTO;
import com.travel.entity.TourPackage;
import com.travel.entity.User;
import com.travel.enums.PackageStatus;
import com.travel.repository.TourPackageRepository;
import com.travel.repository.UserRepository;

@Service
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;

    public TourPackageServiceImpl(
            TourPackageRepository tourPackageRepository,
            UserRepository userRepository) {

        this.tourPackageRepository = tourPackageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TourPackageDTO createPackage(TourPackageDTO dto, String email) {

        User guide = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        TourPackage tourPackage = new TourPackage();

        tourPackage.setPackageName(dto.getPackageName());
        tourPackage.setDestination(dto.getDestination());
        tourPackage.setDescription(dto.getDescription());
        tourPackage.setDuration(dto.getDuration());
        tourPackage.setPrice(dto.getPrice());
        tourPackage.setHotelName(dto.getHotelName());
        tourPackage.setTransportType(dto.getTransportType());
        tourPackage.setActivities(dto.getActivities());
        tourPackage.setAvailableSeats(dto.getAvailableSeats());
        tourPackage.setImageUrl(dto.getImageUrl());

        // Automatically assign the logged-in guide
        tourPackage.setGuide(guide);

        // Every new package starts as PENDING
        tourPackage.setStatus(PackageStatus.PENDING);

        TourPackage savedPackage = tourPackageRepository.save(tourPackage);

        return mapToDTO(savedPackage);
    }
    
    

    @Override
    public List<TourPackageDTO> getAllPackages() {

        return tourPackageRepository
                .findByStatus(PackageStatus.APPROVED)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public TourPackageDTO getPackageById(Long id) {

        TourPackage tourPackage = tourPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        return mapToDTO(tourPackage);
    }

    @Override
    public TourPackageDTO updatePackage(Long id, TourPackageDTO tourPackageDTO) {

        TourPackage tourPackage = tourPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        tourPackage.setPackageName(tourPackageDTO.getPackageName());
        tourPackage.setDestination(tourPackageDTO.getDestination());
        tourPackage.setDescription(tourPackageDTO.getDescription());
        tourPackage.setDuration(tourPackageDTO.getDuration());
        tourPackage.setPrice(tourPackageDTO.getPrice());
        tourPackage.setHotelName(tourPackageDTO.getHotelName());
        tourPackage.setTransportType(tourPackageDTO.getTransportType());
        tourPackage.setActivities(tourPackageDTO.getActivities());
        tourPackage.setAvailableSeats(tourPackageDTO.getAvailableSeats());
        tourPackage.setImageUrl(tourPackageDTO.getImageUrl());

        TourPackage updatedPackage = tourPackageRepository.save(tourPackage);

        return mapToDTO(updatedPackage);
    }

    @Override
    public String deletePackage(Long id) {

        TourPackage tourPackage = tourPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        tourPackageRepository.delete(tourPackage);

        return "Package deleted successfully";
    }

    private TourPackageDTO mapToDTO(TourPackage tourPackage) {

        return new TourPackageDTO(
                tourPackage.getId(),
                tourPackage.getPackageName(),
                tourPackage.getDestination(),
                tourPackage.getDescription(),
                tourPackage.getDuration(),
                tourPackage.getPrice(),
                tourPackage.getHotelName(),
                tourPackage.getTransportType(),
                tourPackage.getActivities(),
                tourPackage.getAvailableSeats(),
                tourPackage.getImageUrl(),
                tourPackage.getStatus()
        );
    }
    
    @Override
    public TourPackageDTO approvePackage(Long id) {

        TourPackage tourPackage = tourPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        tourPackage.setStatus(PackageStatus.APPROVED);

        return mapToDTO(tourPackageRepository.save(tourPackage));
    }
    
    
    @Override
    public TourPackageDTO rejectPackage(Long id) {

        TourPackage tourPackage = tourPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        tourPackage.setStatus(PackageStatus.REJECTED);

        return mapToDTO(tourPackageRepository.save(tourPackage));
    }
    
    @Override
    public List<TourPackageDTO> getPendingPackages() {
        return tourPackageRepository.findByStatus(PackageStatus.PENDING)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<TourPackageDTO> getApprovedPackages() {
        return tourPackageRepository.findByStatus(PackageStatus.APPROVED)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<TourPackageDTO> getPackagesByGuide(Long guideId) {

        return tourPackageRepository.findByGuideId(guideId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    @Override
    public List<TourPackageDTO> searchPackages(String destination) {

        return tourPackageRepository
                .findByDestinationContainingIgnoreCase(destination)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
package com.travel.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.TourPackageDTO;
import com.travel.service.TourPackageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/packages")
@CrossOrigin(origins = "*")
public class TourPackageController {

    private final TourPackageService tourPackageService;

    public TourPackageController(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }

    @PreAuthorize("hasRole('GUIDE')")
    @PostMapping
    public ResponseEntity<TourPackageDTO> createPackage(
            @Valid @RequestBody TourPackageDTO tourPackageDTO,
            Principal principal) {

        return ResponseEntity.ok(
            tourPackageService.createPackage(
                tourPackageDTO,
                principal.getName()
            )
        );
    }

    @GetMapping
    public ResponseEntity<List<TourPackageDTO>> getAllPackages() {
        return ResponseEntity.ok(tourPackageService.getAllPackages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourPackageDTO> getPackageById(@PathVariable Long id) {
        return ResponseEntity.ok(tourPackageService.getPackageById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourPackageDTO> updatePackage( @Valid @PathVariable Long id,
            @RequestBody TourPackageDTO tourPackageDTO) {
        return ResponseEntity.ok(tourPackageService.updatePackage(id, tourPackageDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable Long id) {
        return ResponseEntity.ok(tourPackageService.deletePackage(id));
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<List<TourPackageDTO>> getPendingPackages() {

        return ResponseEntity.ok(
            tourPackageService.getPendingPackages()
        );
    }
    
    

    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<TourPackageDTO>> getGuidePackages(@PathVariable Long guideId) {
        return ResponseEntity.ok(tourPackageService.getPackagesByGuide(guideId));
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<TourPackageDTO> approvePackage(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            tourPackageService.approvePackage(id)
        );
    }
    
    

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<TourPackageDTO> rejectPackage(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            tourPackageService.rejectPackage(id)
        );
    }
    
    
}
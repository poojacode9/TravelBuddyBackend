package com.travel.dto;

import com.travel.enums.PackageStatus;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPackageDTO {

    private Long id;

    @NotBlank(message = "Package name is required")
    private String packageName;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Duration is required")
    private String duration;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Hotel name is required")
    private String hotelName;

    @NotBlank(message = "Transport type is required")
    private String transportType;

    @NotBlank(message = "Activities are required")
    private String activities;

    @NotNull(message = "Available seats are required")
    @Min(value = 1, message = "Available seats must be at least 1")
    private Integer availableSeats;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;
    
    private PackageStatus status;

}
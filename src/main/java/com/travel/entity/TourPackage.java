package com.travel.entity;

import com.travel.enums.PackageStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tour_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;

    private String destination;

    private String description;

    private String duration;

    private Double price;

    private String hotelName;

    private String transportType;

    private String activities;

    private Integer availableSeats;

    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    private PackageStatus status;
    
    @ManyToOne
    @JoinColumn(name = "guide_id")
    private User guide;
}
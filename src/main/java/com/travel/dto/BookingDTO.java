package com.travel.dto;

import java.time.LocalDate;

import com.travel.enums.BookingStatus;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long id;

    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;

    @NotNull(message = "Travel date is required")
    @Future(message = "Travel date must be in the future")
    private LocalDate travelDate;

    @NotNull(message = "Number of persons is required")
    @Min(value = 1, message = "Minimum 1 person is required")
    private Integer numberOfPersons;

    @NotNull(message = "Total amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double totalAmount;

    @NotNull(message = "Status is required")
    private BookingStatus status;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Package ID is required")
    private Long packageId;
    
    
}
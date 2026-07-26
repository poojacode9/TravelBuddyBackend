package com.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.BookingDTO;
import com.travel.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking( @Valid @PathVariable Long id,
                                                    @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }
    
    
 // Customer
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    // Guide
    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByGuide(@PathVariable Long guideId) {
        return ResponseEntity.ok(bookingService.getBookingsByGuide(guideId));
    }

    // Package
    @GetMapping("/package/{packageId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByPackage(@PathVariable Long packageId) {
        return ResponseEntity.ok(bookingService.getBookingsByPackage(packageId));
    }


    // Cancel Booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
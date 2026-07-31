package com.travel.service;

import java.util.List;

import com.travel.dto.BookingDTO;
import com.travel.dto.TicketDTO;
import com.travel.dto.payment.BookingPaymentResponseDTO;

public interface BookingService {

    // CRUD
	BookingPaymentResponseDTO createBooking(BookingDTO bookingDTO);

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(Long id);

    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);

    String deleteBooking(Long id);


    BookingDTO cancelBooking(Long id);

    // Customer
    List<BookingDTO> getBookingsByUser(Long userId);

    // Guide
    List<BookingDTO> getBookingsByGuide(Long guideId);

    // Package
    List<BookingDTO> getBookingsByPackage(Long packageId);
    
    BookingDTO confirmBooking(Long id);
    
    TicketDTO generateTicket(Long bookingId);
}
package com.travel.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.travel.dto.TicketDTO;
import com.travel.entity.Booking;
import com.travel.entity.Ticket;
import com.travel.enums.BookingStatus;
import com.travel.repository.BookingRepository;
import com.travel.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             BookingRepository bookingRepository) {

        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
    }
    @Override
    @Transactional
    public TicketDTO generateTicket(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));


        Ticket existingTicket = ticketRepository
                .findByBookingId(bookingId)
                .orElse(null);


        if (existingTicket != null) {
            return convertToDTO(existingTicket);
        }


        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException(
                "Ticket cannot be generated until payment is completed"
            );
        }


        Ticket ticket = new Ticket();

        ticket.setBooking(booking);
        ticket.setIssueDate(LocalDate.now());

        ticket.setTicketNumber(
            "TB-" + UUID.randomUUID()
            .toString()
            .substring(0,8)
            .toUpperCase()
        );


        Ticket savedTicket = ticketRepository.save(ticket);


        return convertToDTO(savedTicket);
    }
    
    
    
    
    @Transactional
    @Override
    public TicketDTO getTicketByBookingId(Long bookingId) {

    	Ticket ticket = ticketRepository
    	        .findByBookingId(bookingId)
    	        .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket == null) {
            throw new RuntimeException("Ticket not found");
        }

        return convertToDTO(ticket);
    }

    
    
    private TicketDTO convertToDTO(Ticket ticket) {

        Booking booking = ticket.getBooking();

        return new TicketDTO(
                ticket.getTicketNumber(),              
                booking.getId(),                      
                booking.getUser().getName(),           
                booking.getUser().getEmail(),          
                booking.getTourPackage().getPackageName(), 
                booking.getTourPackage().getDestination(), 
                booking.getTravelDate().toString(),    
                booking.getNumberOfPersons(),          
                booking.getTotalAmount(),              
                booking.getStatus().name()             
        );
    }
}
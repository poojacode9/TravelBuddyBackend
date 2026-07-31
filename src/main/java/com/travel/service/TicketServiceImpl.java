package com.travel.service;


import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.travel.entity.Booking;
import com.travel.entity.Ticket;
import com.travel.repository.BookingRepository;
import com.travel.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {


    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;


    public TicketServiceImpl(
            TicketRepository ticketRepository,
            BookingRepository bookingRepository) {

        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
    }


    @Override
    public Ticket generateTicket(Long bookingId) {


        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(
                 () -> new RuntimeException("Booking not found")
                );


        Ticket ticket = new Ticket();

        ticket.setBooking(booking);

        ticket.setTicketNumber(
            "TB-" + UUID.randomUUID()
        );

        ticket.setIssueDate(
            LocalDate.now()
        );


        return ticketRepository.save(ticket);

    }



    @Override
    public Ticket getTicketByBookingId(Long bookingId) {

        return ticketRepository
                .findByBookingId(bookingId)
                .orElseThrow(
                 () -> new RuntimeException("Ticket not found")
                );

    }

}

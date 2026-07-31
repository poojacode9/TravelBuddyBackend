package com.travel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Ticket;


public interface TicketRepository 
        extends JpaRepository<Ticket, Long>{

    Optional<Ticket> findByBookingId(Long bookingId);

}
package com.travel.service;

import com.travel.entity.Ticket;

public interface TicketService {

    Ticket generateTicket(Long bookingId);

    Ticket getTicketByBookingId(Long bookingId);

}
package com.travel.service;

import com.travel.dto.TicketDTO;
import com.travel.entity.Ticket;

public interface TicketService {

	TicketDTO generateTicket(Long bookingId);

    TicketDTO getTicketByBookingId(Long bookingId);

}
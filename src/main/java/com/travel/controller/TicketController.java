package com.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.TicketDTO;
import com.travel.entity.Ticket;
import com.travel.service.TicketService;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {


private final TicketService ticketService;


public TicketController(TicketService ticketService){
    this.ticketService = ticketService;
}



@PostMapping("/{bookingId}")
public ResponseEntity<TicketDTO> generateTicket(@PathVariable Long bookingId) {
    return ResponseEntity.ok(ticketService.generateTicket(bookingId));
}

@GetMapping("/booking/{bookingId}")
public ResponseEntity<TicketDTO> getTicket(@PathVariable Long bookingId) {
    return ResponseEntity.ok(ticketService.getTicketByBookingId(bookingId));
}

}



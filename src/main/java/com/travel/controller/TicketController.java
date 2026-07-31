package com.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public ResponseEntity<Ticket> generateTicket(
        @PathVariable Long bookingId){

    return ResponseEntity.ok(
        ticketService.generateTicket(bookingId)
    );

}



@GetMapping("/booking/{bookingId}")
public ResponseEntity<Ticket> getTicket(
        @PathVariable Long bookingId){

    return ResponseEntity.ok(
        ticketService.getTicketByBookingId(bookingId)
    );

}

}

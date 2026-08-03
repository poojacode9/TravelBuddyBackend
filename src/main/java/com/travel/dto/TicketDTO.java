package com.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private String ticketNumber;
    private Long bookingId;
    private String customerName;
    private String email;
    private String packageName;
    private String destination;
    private String travelDate;
    private Integer numberOfPersons;
    private Double amount;
    private String bookingStatus;



}
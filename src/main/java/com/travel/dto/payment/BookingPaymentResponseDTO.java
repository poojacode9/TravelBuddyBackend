package com.travel.dto.payment;

import com.travel.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPaymentResponseDTO {

    private BookingDTO booking;
    private CreateOrderResponseDTO payment;

}
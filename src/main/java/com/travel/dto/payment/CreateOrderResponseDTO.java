package com.travel.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponseDTO {

    private String razorpayOrderId;
    private Double amount;
    private String key;

}
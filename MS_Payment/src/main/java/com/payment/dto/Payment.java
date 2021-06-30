package com.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {

	private int paymentId;
    private String paymentStatus;
    private String transactionId;
    private String orderId;
    private double amount;

}

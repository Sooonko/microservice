package com.payment.service;

import com.payment.dto.Payment;

public interface PaymentSvc {
	
	
	public Payment makePayment(String userId);

	public String getPaymentStatus();

	public Payment fetchPaymentInfo(String orderId);
}

package com.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.orders.dto.Payment;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

	@PostMapping(value = "/api/payment/doPayment/{userId}")
	Payment makePayment(@PathVariable String userId, Payment payment);
	
}

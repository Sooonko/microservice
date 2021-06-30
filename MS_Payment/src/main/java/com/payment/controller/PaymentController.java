package com.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.Payment;
import com.payment.dto.Product;
import com.payment.service.PaymentSvc;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

	@Autowired
	PaymentSvc paymentSvc;

	@PostMapping(value = "/getPaymentSummary")
	public void showSummary(@RequestBody List<Product> productList) {
		
	}
	
	@PostMapping(value = "/doPayment/{userId}")
	public Payment doPayment(@PathVariable String userId) {
		log.info("Inside doPayment of PaymentController");
		return paymentSvc.makePayment(userId);
	}

	@GetMapping(value = "/getPaymentDetails/{orderId}")
	public Payment getPaymentInfo(@PathVariable String orderId) {
		log.info("Inside getPaymentInfo of PaymentController");
		return paymentSvc.fetchPaymentInfo(orderId);
	}

}

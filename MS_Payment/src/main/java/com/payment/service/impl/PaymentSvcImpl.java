package com.payment.service.impl;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.payment.dto.Payment;
import com.payment.service.PaymentSvc;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentSvcImpl implements PaymentSvc{

	@Override
	public Payment makePayment(String userId) {
		log.info("Inside makePayment of PaymentSvcImpl");
		Payment payment = new Payment();
		payment.setPaymentStatus(getPaymentStatus());
		payment.setTransactionId(UUID.randomUUID().toString());
		return payment;
	}

	@Override
	public String getPaymentStatus() {
		log.info("Inside getPaymentStatus of PaymentSvcImpl");
		return new Random().nextBoolean()?"success":"failed";
	}

	@Override
	public Payment fetchPaymentInfo(String orderId) {
		log.info("Inside fetchPaymentInfo of PaymentSvcImpl");
		int orderNo = Integer.valueOf(orderId);
		//return repo.findByOrderId(orderNo);
		return null;
	}

}

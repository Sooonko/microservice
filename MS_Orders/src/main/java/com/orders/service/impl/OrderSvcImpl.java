package com.orders.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.orders.client.PaymentClient;
import com.orders.dto.Payment;
import com.orders.dto.TransactionResponse;
import com.orders.entity.Order;
import com.orders.entity.Product;
import com.orders.service.OrderSvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderSvcImpl implements OrderSvc {

	private static final String PAYMENT_SVC = "paymentSvc";

	private final PaymentClient paymentClient;
	@Autowired
	private final CircuitBreakerFactory circuitBreakerFactory;
	
	Map<String, List<Product>> cartMap = new HashMap<String, List<Product>>();

	public List<Product> saveItemsInCart(Product product, String userId) {
		log.info("Inside saveItemsInCart of OrderSvcImpl");
		//save the cart items to DB
		List<Product> cartItems;
		if (cartMap.get(userId) == null) {
			cartItems = new ArrayList<Product>();
		} else {
			cartItems = cartMap.get(userId);
		}
		cartItems.add(product);
		cartMap.put(userId, cartItems);
		return cartItems;
	}

	@Override
	public List<Product> showCart(String userId) {
		log.info("Inside showCart of OrderSvcImpl");
		//fetch the cart items from DB
		List<Product> cartItems;
		if (cartMap.get(userId) == null) {
			cartItems = new ArrayList<Product>();
		} else {
			cartItems = cartMap.get(userId);
		}
		return cartItems;
	}
	
	
	@Override
	public TransactionResponse createOrder(String userId) {
		log.info("Inside createOrder of OrderSvcImpl");
		//fetch the cart items from DB
		List<Product> cartItems;
		if (cartMap.get(userId) == null) {
			cartItems = new ArrayList<Product>();
		} else {
			cartItems = cartMap.get(userId);
		}
		
		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString());
		order.setCartItems(cartItems);
		order.setPrice(1993);
		
		Payment payment = new Payment();
		payment.setOrderId(UUID.randomUUID().toString());
		payment.setAmount(1993);
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create(PAYMENT_SVC);
		Supplier<Payment> paymentSupplier = () -> paymentClient.makePayment(userId, payment);
		log.info("Calling Payment API start --> "+LocalDateTime.now());
		Payment paymentResponse = circuitBreaker.run(paymentSupplier, throwable -> handleErrorFallback(throwable));
		log.info("Calling Payment API end --> "+LocalDateTime.now());
		String response = paymentResponse.getPaymentStatus().equals("success")
				? "payment processing successful and order placed"
				: "there is a failure in payment api , order saved to cart";
		
		log.info("response of  of payment api:- "+response);
		
		return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(),
				response);
	}

	private Payment handleErrorFallback(Throwable t) {
		log.info("Payment failed, circuitBreaker method handleError invoked... ");
		log.error(t.getMessage());
		Payment payment = new Payment();
		payment.setPaymentStatus("failed");
		return payment;
	}

}

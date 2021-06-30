package com.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orders.dto.TransactionResponse;
import com.orders.entity.Product;
import com.orders.service.OrderSvc;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrdersController {

	@Autowired
	OrderSvc orderSvc;

	@PostMapping(value = "/saveToCart/{userId}")
	public String saveToCart(@RequestBody Product product, @PathVariable String userId) {
		log.info("Inside saveToCart of OrdersController");
		List<Product> cartItems = orderSvc.saveItemsInCart(product, userId);
		System.out.println("cartSize: " + cartItems.size());
		if (cartItems.size() > 0) {
			return "Item added to cart";
		} else {
			return "Error while saving item to cart";
		}
	}

	@GetMapping(value = "/displayCart/{userId}")
	public List<Product> showItemsInCart(@PathVariable String userId) {
		log.info("Inside showItemsInCart of OrdersController");
		List<Product> cartItems = orderSvc.showCart(userId);
		System.out.println("cartSize: " + cartItems.size());
		return cartItems;
	}

	@PostMapping(value = "/checkout/{userId}")
	public TransactionResponse doCheckout(@PathVariable String userId) {
		log.info("Inside doCheckout of OrdersController");
		return orderSvc.createOrder(userId);
	}

}

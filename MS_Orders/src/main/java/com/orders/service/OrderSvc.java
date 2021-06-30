package com.orders.service;

import java.util.List;

import com.orders.dto.TransactionResponse;
import com.orders.entity.Product;

public interface OrderSvc {

	List<Product> saveItemsInCart(Product product, String userId);

	List<Product> showCart(String userId);

	TransactionResponse createOrder(String userId);

}

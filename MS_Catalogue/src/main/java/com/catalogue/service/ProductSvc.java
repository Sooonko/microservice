package com.catalogue.service;

import java.util.List;

import com.catalogue.entity.Product;

public interface ProductSvc {
	
	public List<Product> getAllProducts();

	public Product getProductByName(String productName);

	String saveToCart(Product product);

}

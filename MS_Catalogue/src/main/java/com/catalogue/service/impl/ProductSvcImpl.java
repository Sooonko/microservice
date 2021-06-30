package com.catalogue.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.catalogue.entity.Product;
import com.catalogue.service.ProductSvc;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductSvcImpl implements ProductSvc {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<Product> getAllProducts() {
		log.info("Inside getAllProducts of ProductSvcImpl");
		Product prod1 = new Product(new Long(1), "RedmiNote4", "Xiaomi Mobile Phone", new Double(13999), "Mobile");
		Product prod2 = new Product(new Long(2), "Samsung Galaxy M51", "Samsung Mobile Phone", new Double(22999),
				"Mobile");
		List<Product> productList = new ArrayList<Product>();
		productList.add(prod1);
		productList.add(prod2);
		return productList;
	}

	@Override
	public Product getProductByName(String productName) {
		log.info("Inside getProductByName of ProductSvcImpl");
		List<Product> productsList = (List<Product>) getAllProducts();
		return productsList.stream().filter(p -> productName.equals(p.getName())).findAny().orElse(null);
	}
	
	
	/*
	 * https://github.com/Netflix/Hystrix/wiki/Configuration
	 */
	
	@Override
	@HystrixCommand(fallbackMethod = "fallBackForSaveToCart", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50") }, 
			threadPoolKey = "addToCartPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "10"),
					@HystrixProperty(name = "maxQueueSize", value = "5") })
	public String saveToCart(Product product) {
		log.info("Inside saveToCart of ProductSvcImpl");
		String status = restTemplate.postForObject("http://ORDERS-SERVICE/api/orders/saveToCart/A", product, String.class);
		log.info("status of saveToCart in ProductSvcImpl :- "+status);
		return status;
	}
	
	
	//A fallback method should be defined in the same class where is HystrixCommand.
	//Also a fallback method should have same signature,return type to a method which was invoked as hystrix command
	@SuppressWarnings("unused")
	private String fallBackForSaveToCart(Product product) {
		log.info("Circuit breaker enabled. Order service is not responding... Fallback method invoked..");
        return "false";
	}

	//If you need a common behavior whenever a failure occurs then you need not declare a separate fallback method
	//for separate HysrixCommand.Instead, you could have only one default fallback method at class
}

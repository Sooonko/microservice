package com.catalogue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalogue.entity.Product;
import com.catalogue.service.ProductSvc;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/catalogue")
@Slf4j
public class CatalogueController {

	@Autowired
	ProductSvc productSvc;

	@GetMapping(value = "/homepageCatalogue")
	public List<Product> getProductsForHomepage() {
		log.info("Inside getProductsForHomepage of CatalogueController");
		return productSvc.getAllProducts();
	}
	
	@PostMapping(value = "/addToCart")
	public String saveToCart(@RequestBody Product product) {
		log.info("Inside saveToCart of CatalogueController");
		return productSvc.saveToCart(product);
	}
	

}

package com.catalogue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

	private String orderId;
	private int quantity;
	private double price;
	private String name;

}

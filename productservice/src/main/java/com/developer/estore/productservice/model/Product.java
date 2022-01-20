package com.developer.estore.productservice.model;

import java.math.BigDecimal;

import lombok.Data;

@Data	
public class Product {
	
	private String title;
	private BigDecimal price;
	private Integer quantity;

}

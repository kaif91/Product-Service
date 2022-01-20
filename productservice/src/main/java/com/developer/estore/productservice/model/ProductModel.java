package com.developer.estore.productservice.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductModel {

	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

}

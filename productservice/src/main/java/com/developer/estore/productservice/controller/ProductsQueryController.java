package com.developer.estore.productservice.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.estore.productservice.model.ProductModel;
import com.developer.estore.productservice.query.FindProductsQuery;

@RestController
@RequestMapping("/product")
public class ProductsQueryController {
	
	@Autowired
	QueryGateway gateway;
	
	@GetMapping
	public List<ProductModel> getProducts(){
		
		FindProductsQuery query=new FindProductsQuery();
		List<ProductModel> result =gateway.query(query, ResponseTypes.multipleInstancesOf(ProductModel.class))
				.join();
		return result;
	}

}


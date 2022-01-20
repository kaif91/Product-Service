package com.developer.estore.productservice.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.estore.productservice.command.CreateProductCommand;
import com.developer.estore.productservice.model.Product;

@RestController
@RequestMapping(path="/product")
public class ProductController {
	
	
	private CommandGateway gateway;
	
	@Autowired
	public ProductController(CommandGateway gateway) {
		this.gateway=gateway;
	}
	
	
	@PostMapping
	public String createProduct(@RequestBody Product product) {
		// create product command
		CreateProductCommand createProductCommand =CreateProductCommand.builder()
		.price(product.getPrice())
		.quantity(product.getQuantity())
		.title(product.getTitle())
		.productId(UUID.randomUUID().toString())
		.build();
		String result=null;
		try {
			//send command to command bus
		 result=gateway.sendAndWait(createProductCommand);
		}
		catch(Exception e)
		{
			result=e.getLocalizedMessage();
		}
		return result;
	}

}

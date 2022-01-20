package com.developer.estore.productservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

import com.developer.estore.productservice.core.event.ProductCreatedEvent;
import com.developer.estore.productservice.data.ProductLookupEntity;
import com.developer.estore.productservice.data.ProductLookupRepository;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {
	
	private final ProductLookupRepository repo;
	public ProductLookupEventsHandler(ProductLookupRepository repo) {
		this.repo=repo;
	}
	
	public void on(ProductCreatedEvent event) {
		ProductLookupEntity entity = new ProductLookupEntity(event.getProductId(),event.getTitle());
		repo.save(entity);
	}
	
	@ResetHandler
	public void reset() {
		repo.deleteAll();
	}

}

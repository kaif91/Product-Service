package com.developer.estore.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.developer.estore.core.events.ProductReservationCanceledEvent;
import com.developer.estore.core.events.ProductReservedEvent;
import com.developer.estore.productservice.core.event.ProductCreatedEvent;
import com.developer.estore.productservice.data.ProductEntity;
import com.developer.estore.productservice.data.ProductRepository;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
	
	private final ProductRepository repo;
	
	public ProductEventsHandler(ProductRepository repo) {
		this.repo=repo;
	}
	

	@EventHandler
	public void on(ProductCreatedEvent event) {
		
		ProductEntity entity=new ProductEntity();
		BeanUtils.copyProperties(event, entity);
		repo.save(entity);
		
	}
	@EventHandler
	public void on(ProductReservedEvent event) {
		ProductEntity entity = repo.findByProductId(event.getProductId());
		entity.setQuantity(entity.getQuantity()-event.getQuantity());
		repo.save(entity);
	}
	
	@EventHandler
	public void on(ProductReservationCanceledEvent event) {
		ProductEntity entity = repo.findByProductId(event.getProductId());
		entity.setQuantity(entity.getQuantity()+event.getQuantity());
		repo.save(entity);
	}
	
	@ResetHandler
	public void reset() {
		repo.deleteAll();
	}
}

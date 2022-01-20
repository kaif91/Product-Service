package com.developer.estore.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.developer.estore.core.command.CancelProductReservationCommand;
import com.developer.estore.core.command.ReserveProductCommand;
import com.developer.estore.core.events.ProductReservationCanceledEvent;
import com.developer.estore.core.events.ProductReservedEvent;
import com.developer.estore.productservice.core.event.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductAggregate() {}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
			throw new IllegalArgumentException("Price can not be less than 0");
		}
		
		ProductCreatedEvent productCreatedEvent= new ProductCreatedEvent();
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		AggregateLifecycle.apply(productCreatedEvent);
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
			this.productId=productCreatedEvent.getProductId();
			this.title=productCreatedEvent.getTitle();
			this.price=productCreatedEvent.getPrice();
			this.quantity=productCreatedEvent.getQuantity();
	}
	
	
	@CommandHandler
	public void handle(ReserveProductCommand command) {
		if(quantity < command.getQuantity()) {
			throw new IllegalArgumentException("Insufficient number of items in stock");
		}
		ProductReservedEvent event = ProductReservedEvent.builder()
				.productId(command.getProductId())
				.orderId(command.getOrderId())
				.userId(command.getUserId())
				.quantity(command.getQuantity())
				.build();
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(ProductReservedEvent event) {
		this.quantity -=event.getQuantity();
	}
	
	@CommandHandler
    public void handle(CancelProductReservationCommand command) {
    
    	ProductReservationCanceledEvent event = ProductReservationCanceledEvent.builder()
    			.productId(command.getProductId())
    			.orderId(command.getOrderId())
    			.quantity(command.getQuantity())
    			.userId(command.getUserId())
    			.reason(command.getReason())
    			.build();
    	AggregateLifecycle.apply(event);
    }
    
    @EventSourcingHandler
    public void on(ProductReservationCanceledEvent event) {
    	this.quantity+=event.getQuantity();
    }

}

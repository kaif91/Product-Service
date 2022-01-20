package com.developer.estore.productservice.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import com.developer.estore.productservice.command.CreateProductCommand;
import com.developer.estore.productservice.data.ProductLookupEntity;
import com.developer.estore.productservice.data.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	private final ProductLookupRepository repo;

	public CreateProductCommandInterceptor(ProductLookupRepository repo) {
		this.repo = repo;
	}

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		return (index, command) -> {

			if (CreateProductCommand.class.equals(command.getPayloadType())) {
				CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
				ProductLookupEntity entity = repo.findByProductIdOrTitle(createProductCommand.getProductId(),
						createProductCommand.getTitle());
				if (entity != null) {
					throw new IllegalStateException("Product with given product id" + entity.getProductId()
							+ " or title " + entity.getTitle() + " already exists");
				}
			}
			return command;
		};
	}

}

package com.developer.estore.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import com.developer.estore.productservice.command.interceptor.CreateProductCommandInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	/*String header="AB=AP125645";
	//List<String> certValues = Arrays.asList(header.split("#")[0].split(","));
	Optional<String> idOptional =Stream.of(header.split("#")[0].split(",")).filter(s->s.contains("CN=")).findFirst();
	if(idOptional.isPresent())
	System.out.println(idOptional.get().split("=")[1]);
	else
		System.out.println("NULL");*/
	}
	
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context,CommandBus bus) {
		bus.registerDispatchInterceptor(
		context.getBean(CreateProductCommandInterceptor.class)
		);
	}

}

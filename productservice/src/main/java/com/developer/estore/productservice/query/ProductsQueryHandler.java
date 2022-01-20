package com.developer.estore.productservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.developer.estore.productservice.data.ProductEntity;
import com.developer.estore.productservice.data.ProductRepository;
import com.developer.estore.productservice.model.ProductModel;

@Component
public class ProductsQueryHandler {

	
	private final ProductRepository repo;
	
	ProductsQueryHandler(ProductRepository repo){
		this.repo=repo;
	}
	
	@QueryHandler
	public List<ProductModel> findProducts(FindProductsQuery query) {
		List<ProductModel> products =  new ArrayList<>();
		List<ProductEntity> entities = repo.findAll();
		for(ProductEntity e:entities) {
			ProductModel model =new ProductModel();
			BeanUtils.copyProperties(e, model);
			products.add(model);
		}
		return products;
	}
	
}

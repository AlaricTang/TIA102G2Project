package com.xyuan.productOrder.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_ProductOrder;

@Service("productOrderService")
public class ProductOrderService {
	
	@Autowired
	ProductOrderRepository repository;
	

	
	public void addProductOrder(ProductOrderVO productOrderVO) {
		repository.save(productOrderVO);
	}
	
	public void updateProductOrder(ProductOrderVO productOrderVO) {
		repository.save(productOrderVO);
	}
	
	public void deleteProductOrder(Integer productOrderID) {
		if(repository.existsById(productOrderID))
			repository.deleteByProductOrderID(productOrderID);
	}
	
	public ProductOrderVO getOneProductOrder (Integer productOrderID) {
		Optional<ProductOrderVO> optional = repository.findById(productOrderID);
		return optional.orElse(null);
	}
	
	public List<ProductOrderVO> getAll(){
		return repository.findAll();
	}
	
	public List<ProductOrderVO> getAll(Map<String, String> map){
		return CompositeQuery_ProductOrder.getAllC(map);
	}
	
}

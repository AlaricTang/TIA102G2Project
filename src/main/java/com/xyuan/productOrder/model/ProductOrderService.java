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
	

	
	public ProductOrderVO addProductOrder(ProductOrderVO productOrderVO) {
		return repository.save(productOrderVO);
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

	public List<ProductOrderVO> getAllUserProductOrder(Integer userID) {
		return repository.getAllUserProductOrder(userID);
	}

	public List<ProductOrderVO> getAllUndone() {
		return repository.getAllUndoneProductOrder();
	}

//	public ProductOrderVO addandGetProductOrder(ProductOrderVO productOrderVO) {
//		ProductOrderVO saveDrinkOrderVO = repository.save(productOrderVO);
//		return saveDrinkOrderVO;
//	}
	
}

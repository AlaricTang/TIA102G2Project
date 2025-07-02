package com.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.entity.ProductOrderVO;
import com.repository.ProductOrderRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_ProductOrder;

@Service("productOrderService")
public class ProductOrderService {
	
	@Autowired
	ProductOrderRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
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
		Optional<List<ProductOrderVO>> optional = Optional.ofNullable(CompositeQuery_ProductOrder.getAllC(map,sessionFactory.openSession()));
		return optional.orElse(null);
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

package com.xyuan.productOrderDetail.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_DrinkOrderDetail;
import hibernate.util.compositeQuery.CompositeQuery_ProductOrderDetail;

@Service("productOrderDetailService")
public class ProductOrderDetailService {

	@Autowired
	ProductOrderDetailRepository repository;
	
	public void addProductOrderDetail(ProductOrderDetailVO productOrderDetailVO) {
		repository.save(productOrderDetailVO);
	}
	
	public void updateProductOrderDetail(ProductOrderDetailVO productOrderDetailVO) {
		repository.save(productOrderDetailVO);
	}
	
	public void deleteProductOrderDetail(Integer productOrderDetailID) {
		if(repository.existsById(productOrderDetailID))
			repository.deleteByProductOrderDetailID(productOrderDetailID);
	}
	
	public ProductOrderDetailVO getOneProductOrderDetail (Integer productOrderDetailID) {
		Optional<ProductOrderDetailVO> optional = repository.findById(productOrderDetailID);
		return optional.orElse(null);
	}
	
	public List<ProductOrderDetailVO> getAll(){
		return repository.findAll();
	}

	//複合查詢堂安說用不到
//	public List<ProductOrderDetailVO> getAll(Map<String, String[]> map){
//		return CompositeQuery_ProductOrderDetail.getAllC(map);
//	}

	public List<ProductOrderDetailVO> getByProductOrderID(Integer productOrderID) {
		return repository.getByProductOrderID(productOrderID);
	}
	
	
}

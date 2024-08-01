package com.xyuan.productOrderDetail.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
}

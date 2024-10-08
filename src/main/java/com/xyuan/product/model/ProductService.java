package com.xyuan.product.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.jibeiProduct.model.JibeiProductVO;

@Service("productService")
public class ProductService {
	
	@Autowired
	ProductRepository repository;
	
	public void addProduct(ProductVO productVO) {
		repository.save(productVO);
	}
	
	public void updateProduct(ProductVO productVO) {
		repository.save(productVO);
	}
	
	public ProductVO getOneProduct (Integer productID) {
		Optional<ProductVO> optional = repository.findById(productID);
		return optional.orElse(null);
	}

	public List<ProductVO> getAll() {
		return repository.findAll();
	}

	public List<ProductVO> getByTag(Byte productTag) {
		List<ProductVO> productTagList = repository.getByTag(productTag);
		return productTagList;
	}
	
	public List<ProductVO> getOnProduct(){
		return repository.getProductByStatus(1);
	}

	public List<ProductVO> getOffProduct(){
		return repository.getProductByStatus(0);
	}
	
	
}

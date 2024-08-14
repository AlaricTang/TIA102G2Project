package com.tang.jibeiProduct.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_JibeiProduct;

@Service("jibeiProductService")
public class JibeiProductService {

	@Autowired
	JibeiProductRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;

	public void addJibeiProduct(JibeiProductVO jibeiProductVO) {
		repository.save(jibeiProductVO);
	}

	public JibeiProductVO updateJibeiProduct(JibeiProductVO jibeiProductVO) {
		return repository.save(jibeiProductVO);
	}

	public void deleteJibeiProduct(Integer jibeiProductID) {
		if (repository.existsById(jibeiProductID))
			repository.deleteByJibeiProductID(jibeiProductID);
	}

	public JibeiProductVO getOneJibeiProduct(Integer jibeiProductID) {
		Optional<JibeiProductVO> optional = repository.findById(jibeiProductID);
		return optional.orElse(null);
	}

	public List<JibeiProductVO> getAll() {
		return repository.findAll();
	}
	
	public List<JibeiProductVO> getAll(Map<String, String[]> map) {
		return CompositeQuery_JibeiProduct.getAllC(map, sessionFactory.openSession());
	}
	
	public List<JibeiProductVO> getOnJibeiProduct(){
		return repository.getJibeiProductByStatus(1);
	}

	public List<JibeiProductVO> getOffJibeiProduct(){
		return repository.getJibeiProductByStatus(0);
	}

	//可能用不到
//	public List<JibeiProductVO> getAll(Map<String, String[]> map) {
//		return CompositeQuery_DrinkOrder.getAllC(map);
//	}
}

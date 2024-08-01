package com.tang.jibeiProduct.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jibeiProductService")
public class JibeiProductService {

	@Autowired
	JibeiProductRepository repository;

	public void addDrinkOrder(JibeiProductVO jibeiProductVO) {
		repository.save(jibeiProductVO);
	}

	public void updateDrinkOrder(JibeiProductVO jibeiProductVO) {
		repository.save(jibeiProductVO);
	}

	public void deleteDrinkOrder(Integer jibeiProductID) {
		if (repository.existsById(jibeiProductID))
			repository.deleteByJibeiProductID(jibeiProductID);
	}

	public JibeiProductVO getOneDrinkOrder(Integer jibeiProductID) {
		Optional<JibeiProductVO> optional = repository.findById(jibeiProductID);
		return optional.orElse(null);
	}

	public List<JibeiProductVO> getAll() {
		return repository.findAll();
	}

	//可能用不到
//	public List<JibeiProductVO> getAll(Map<String, String[]> map) {
//		return CompositeQuery_DrinkOrder.getAllC(map);
//	}
}

package com.tang.drinkOrder.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_DrinkOrder;

@Service("drinkOrderService")
public class DrinkOrderService {
	
	@Autowired
	DrinkOrderRepository repository;
	
	
	public DrinkOrderVO addAndGetDrinkOrder(DrinkOrderVO drinkOrderVO) {
		DrinkOrderVO saveDrinkOrderVO = repository.save(drinkOrderVO);
		return saveDrinkOrderVO;
	}
	
	public void updateDrinkOrder(DrinkOrderVO drinkOrderVO) {
		repository.save(drinkOrderVO);
	}
	
	public void deleteDrinkOrder(Integer drinkOrderID) {
		if(repository.existsById(drinkOrderID))
			repository.deleteByDrinkOrderID(drinkOrderID);
	}
	
	public DrinkOrderVO getOneDrinkOrder (Integer drinkOrderID) {
		Optional<DrinkOrderVO> optional = repository.findById(drinkOrderID);
		return optional.orElse(null);
	}
	
	public List<DrinkOrderVO> getAll(){
		return repository.findAll();
	}
	
	public List<DrinkOrderVO> getAll(Map<String, String[]> map){
		return CompositeQuery_DrinkOrder.getAllC(map);
	}
}

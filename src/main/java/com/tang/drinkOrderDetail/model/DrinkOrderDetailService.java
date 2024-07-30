package com.tang.drinkOrderDetail.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_DrinkOrderDetail;


@Service("drinkOrderDetailService")
public class DrinkOrderDetailService {

	@Autowired
	DrinkOrderDetailRepository repository;
	
	
	public void addDrinkOrderDetail(DrinkOrderDetailVO drinkOrderDetailVO) {
		repository.save(drinkOrderDetailVO);
	}
	
	public void updateDrinkOrderDetail(DrinkOrderDetailVO drinkOrderDetailVO) {
		repository.save(drinkOrderDetailVO);
	}
	
	public void deleteDrinkOrderDetail(Integer drinkOrderDetailID) {
		if(repository.existsById(drinkOrderDetailID))
			repository.deleteByDrinkOrderDetailID(drinkOrderDetailID);
	}
	
	//not need getOne
	public List<DrinkOrderDetailVO> getAll(){
		return repository.findAll();
	}
	
	//此為複合查詢??
	public List<DrinkOrderDetailVO> getAll(Map<String, String[]> map){
		return CompositeQuery_DrinkOrderDetail.getAllC(map);
	}
}

package com.tang.drinkOrderDetail.model;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.HibernateUtil_CompositeQuery_DrinkOrderDetail;

@Service("drinkOrderDetailService")
public class DrinkOrderDetailService {

	@Autowired
	DrinkOrderDetailRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	public List<DrinkOrderDetailVO> getAll(){
		return repository.findAll();
	}
	

	
	public List<DrinkOrderDetailVO> getAll(Map<String, String[]> map){
		return HibernateUtil_CompositeQuery_DrinkOrderDetail.getAllC(map, sessionFactory.openSession());
	}
}

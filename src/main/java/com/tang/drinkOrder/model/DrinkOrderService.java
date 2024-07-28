package com.tang.drinkOrder.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.HibernateUtil_CompositeQuery_DrinkOrder;

@Service("drinkOrderService")
public class DrinkOrderService {
	
	@Autowired
	DrinkOrderRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addDrinkOrder(DrinkOrderVO drinkOrderVO) {
		repository.save(drinkOrderVO);
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
		return HibernateUtil_CompositeQuery_DrinkOrder.getAllC(map, sessionFactory.openSession());
	}
}

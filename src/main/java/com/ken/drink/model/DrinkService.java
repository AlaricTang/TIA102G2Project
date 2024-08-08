package com.ken.drink.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_Drink;

@Service("drinkService")
public class DrinkService {

	@Autowired
	DrinkRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addDrink(DrinkVO drinkVO) {
		repository.save(drinkVO);
	}
	
	public void updateDrink(DrinkVO drinkVO) {
		repository.save(drinkVO);
	}
	
	public void deleteDrink(Integer drinkID) {
		if(repository.existsById(drinkID)) {
			repository.deleteByDrinkID(drinkID);
		}
	}
	
	public DrinkVO getOneDrink(Integer drinkID) {
		Optional<DrinkVO> optional = repository.findById(drinkID);
		return optional.orElse(null);
	}
	
	public List<DrinkVO> getAll(){
		return repository.findAll();
	}
	
	public List<DrinkVO> getAll(Map<String, String[]> map){
		return CompositeQuery_Drink.getAllC(map, sessionFactory.openSession()); // Session可以刪掉
	}
	
	// 根據標籤查詢飲品
    public List<DrinkVO> getDrinksByTag(String drinkTag) {
        return repository.findByDrinkTag(drinkTag);
    }
}

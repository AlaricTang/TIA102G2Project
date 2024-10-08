package com.tang.drinkOrder.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_DrinkOrder;

@Service("drinkOrderService")
public class DrinkOrderService {
	
	@Autowired
	DrinkOrderRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public DrinkOrderVO addAndGetDrinkOrder(DrinkOrderVO drinkOrderVO) {
		DrinkOrderVO saveDrinkOrderVO = repository.save(drinkOrderVO);
		return saveDrinkOrderVO;
	}
	

	public DrinkOrderVO getOneDrinkOrder (Integer drinkOrderID) {
		Optional<DrinkOrderVO> optional = repository.findById(drinkOrderID);
		return optional.orElse(null);
	}
	
	public List<DrinkOrderVO> getAll(){
		return repository.findAll();
	}	
	public List<DrinkOrderVO> getAllUndone(){
		return repository.getAllUndoneDrinkOrder();
	}
	
	public List<DrinkOrderVO> getAllByStoreID(Integer storeID){
		return repository.getAllStoreDrinkOrder(storeID);
	}
	public List<DrinkOrderVO> getAllUndoneByStoreID(Integer storeID){
		return repository.getAllStoreUndoneDrinkOrder(storeID);
	}
	
	public List<DrinkOrderVO> getAllUserDrinkOrder(Integer userID){
		return repository.getAllUserDrinkOrder(userID);
	}
	
	public List<DrinkOrderVO> getAll(Map<String, String> map){
		Optional<List<DrinkOrderVO>> optional = Optional.ofNullable(CompositeQuery_DrinkOrder.getAllC(map,sessionFactory.openSession()));
		return optional.orElse(null);
	}
	
	public void updateDrinkOrder(DrinkOrderVO drinkOrderVO) {
		repository.save(drinkOrderVO);
	}
	
	//沒用到
	public void deleteDrinkOrder(Integer drinkOrderID) {
		if(repository.existsById(drinkOrderID))
			repository.deleteByDrinkOrderID(drinkOrderID);
	}
	
	//沒用到
	public DrinkOrderVO getOneUndoneDrinkOrder (Integer drinkOrderID) {
		Optional<DrinkOrderVO> optional = Optional.ofNullable(repository.getOneUndoneDrinkOrder(drinkOrderID));
		return optional.orElse(null);
	}
}

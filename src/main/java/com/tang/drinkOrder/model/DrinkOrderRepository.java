package com.tang.drinkOrder.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DrinkOrderRepository extends JpaRepository<DrinkOrderVO, Integer> {

	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
	@Query(value = "delete from drinkorder where drinkOrderID =?1", nativeQuery = true) //就是給SQL，nativeQuery預設為false 表示使用JQL
	void deleteByDrinkOrderID(int drinkOrderID);
	
	@Query(value = "select * from drinkorder where drinkOrderID =?1 and drinkOrderStatus = 0", nativeQuery = true)
	DrinkOrderVO getOneUndoneDrinkOrder(int drinkOrderID);
	
	@Query(value = "select * from drinkorder where drinkOrderStatus = 0", nativeQuery = true)
	List<DrinkOrderVO> getAllUndoneDrinkOrder();
	
	@Query(value = "select * from drinkorder where userID =?1 order by drinkOrderCreateTime DESC;", nativeQuery = true)
	List<DrinkOrderVO> getAllUserDrinkOrder(Integer userID);

	@Query(value = "select * from drinkorder where storeID =?1 ", nativeQuery = true)
	List<DrinkOrderVO> getAllStoreDrinkOrder(Integer storeID);
	
	@Query(value = "select * from drinkorder where storeID =?1 and drinkOrderStatus = 0", nativeQuery = true)
	List<DrinkOrderVO> getAllStoreUndoneDrinkOrder(Integer userID);
}


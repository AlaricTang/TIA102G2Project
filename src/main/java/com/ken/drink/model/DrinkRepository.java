package com.ken.drink.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DrinkRepository extends JpaRepository<DrinkVO , Integer>{
	
	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
	@Query(value = "delete from drink where drinkID =?1", nativeQuery = true)
	void deleteByDrinkID(int drinkID);
	
	// 根據標籤查詢飲品
	
    List<DrinkVO> findByDrinkTagAndDrinkStatus(String drinkTag, Byte drinkStatus);

}

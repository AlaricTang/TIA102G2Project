package com.tang.drinkOrderDetail.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DrinkOrderDetailRepository extends JpaRepository<DrinkOrderDetailVO, Integer>{

	
	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
	@Query(value = "delete from drinkorderdetail where drinkOrderDetailID =?1", nativeQuery = true) //就是給SQL，nativeQuery預設為false 表示使用JQL 
	void deleteByDrinkOrderDetailID(int drinkOrderDetailID);
}

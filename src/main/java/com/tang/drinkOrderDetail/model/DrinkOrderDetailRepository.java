package com.tang.drinkOrderDetail.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DrinkOrderDetailRepository extends JpaRepository<DrinkOrderDetailVO, Integer>{

	
	@Transactional
	@Modifying
	@Query(value = "delete from drinkorderdetail where drinkOrderDetailID =?1", nativeQuery = true)
	void deleteByDrinkOrderDetailID(int drinkOrderDetailID);
}

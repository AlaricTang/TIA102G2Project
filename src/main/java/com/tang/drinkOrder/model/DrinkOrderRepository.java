package com.tang.drinkOrder.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DrinkOrderRepository extends JpaRepository<DrinkOrderVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from drinkorder where drinkOrderID =?1", nativeQuery = true)
	void deleteByDrinkOrderID(int drinkOrderID);
}
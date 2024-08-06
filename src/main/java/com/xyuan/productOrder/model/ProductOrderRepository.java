package com.xyuan.productOrder.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOrderRepository extends JpaRepository<ProductOrderVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from ProductOrder where ProductOrderID = ?1", nativeQuery = true)
	void deleteByProductOrderID(int productOrderID);

	@Query(value = "select * from productorder where userID =?1 ", nativeQuery = true)
	List<ProductOrderVO> getAllUserProductOrder(Integer userID);

	@Query(value = "select * from productorder where productOrderStatus = 0", nativeQuery = true)
	List<ProductOrderVO> getAllUndoneProductOrder();
	
}

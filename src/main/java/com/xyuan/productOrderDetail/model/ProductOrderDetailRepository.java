package com.xyuan.productOrderDetail.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetailVO, Integer> {

	@Transactional
	@Modifying
	@Query(value="delete from productOrderDetail where productOrderDetailID=?1", nativeQuery = true)
	void deleteByProductOrderDetailID(int productOrderDetailID);

	@Query(value = "select * from productorderdetail where productOrderID = ?1", nativeQuery = true)
	List<ProductOrderDetailVO> getByProductOrderID(Integer productOrderID);
}

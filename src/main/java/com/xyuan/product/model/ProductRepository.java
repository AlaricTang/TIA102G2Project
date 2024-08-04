package com.xyuan.product.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<ProductVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from Product where ProductID = ?1", nativeQuery = true)
	void deleteByProductID(int productID);
	
	@Query(value="select * from product where productTag = ?1", nativeQuery = true)
	List<ProductVO> getByTag(Byte productTag);
}


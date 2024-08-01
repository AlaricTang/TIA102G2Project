package com.tang.jibeiProduct.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JibeiProductRepository extends JpaRepository<JibeiProductVO, Integer>{

	@Transactional
	@Modifying
	@Query(value ="delete from jibeiproduct where jibeiProductID =?1", nativeQuery = true)
	void deleteByJibeiProductID(int jibeiProductID);
}

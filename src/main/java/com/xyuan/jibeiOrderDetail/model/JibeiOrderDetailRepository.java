package com.xyuan.jibeiOrderDetail.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JibeiOrderDetailRepository extends JpaRepository<JibeiOrderDetailVO, Integer>{

	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
	@Query(value = "delete from jibeiOrderDetail where jibeiOrderDetailID =?1", nativeQuery = true) //就是給SQL，nativeQuery預設為false 表示使用JQL
	void deleteByJibeiOrderDetailID(int jibeiOrderDetailID);

	@Query(value="select * from jibeiOrderDetail where productOrderID =?1", nativeQuery = true)
	List<JibeiOrderDetailVO> getByProductOrderID(Integer productOrderID);
	
	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
	@Query(value = "INSERT INTO jibeiOrderDetail (productOrderID, jibeiProductID, jibeiOrderDetailAmount, jibeiOrderDetailPrice) " +
            "VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)//就是給SQL，nativeQuery預設為false 表示使用JQL
	void insertJibeiOrderDetail(Integer productOrderID, Integer jibeiProductID, 
			Integer jibeiOrderDetailAmount, Integer jibeiOrderDetailPrice);
	
}

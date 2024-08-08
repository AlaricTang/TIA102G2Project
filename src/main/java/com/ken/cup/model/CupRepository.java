package com.ken.cup.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CupRepository extends JpaRepository<CupVO, Integer>{
	
	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
	@Query(value = "delete from cup where cupID =?1", nativeQuery = true)
	void deleteByCupID(int cupID);

	@Query(value = "SELECT COUNT(*) FROM cup WHERE storeID = :storeID AND cupStatus = 0", nativeQuery = true)
	Long countByStoreIDAndCupStatus(@Param("storeID") Integer storeID);
}

package com.ellie.store.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StoreRepository extends JpaRepository<StoreVO, Integer>{
	
	@Transactional // 定義此方法為transaction 若發生錯誤 幫你RollBack
    @Modifying // 告知此方法為修改操作，沒寫JPA不會給你修改
    @Query(value = "delete from store where storeID = ?1", nativeQuery = true)
    void deleteByStoreID(int storeID);

}

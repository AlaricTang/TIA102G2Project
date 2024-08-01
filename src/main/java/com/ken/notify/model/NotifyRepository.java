package com.ken.notify.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotifyRepository extends JpaRepository<NotifyVO, Integer>{

	
//	@Transactional //定義此方法為transaction 若發生錯誤 幫你rollBack
//	@Modifying //告知此方法為修改操作，沒寫JPA不會給你修改
//	@Query(value = "delete from notify where notifyID =?1", nativeQuery = true)
//	void deleteByNotifyID(int notifyID);
	
	
    @Query("FROM NotifyVO WHERE userID=?1 ORDER BY notifyTime DESC")
    List<NotifyVO> findByUserIDOrderByNotifyTimeDesc(int userID);
    
}

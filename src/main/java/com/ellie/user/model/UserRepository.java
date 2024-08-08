package com.ellie.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserVO, Integer> {

    @Transactional // 定義此方法為transaction 若發生錯誤 幫你RollBack
    @Modifying // 告知此方法為修改操作，沒寫JPA不會給你修改
    @Query(value = "delete from user where userID = ?1", nativeQuery = true)
    void deleteByUserID(int userID);
    
    // 根據電子郵件地址查詢用戶
    UserVO findByUserEmail(String email);
}



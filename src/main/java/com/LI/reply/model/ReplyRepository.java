// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.LI.reply.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyRepository extends JpaRepository<ReplyVO, Integer> {
	 List<ReplyVO> findByCustomerID(int customerID); //Reply表格查詢特定客戶留言+客服回覆用
}
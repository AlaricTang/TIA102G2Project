// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.repository;

import java.util.List;

import com.entity.ReplyVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyVO, Integer> {
	 List<ReplyVO> findByCustomerID(int customerID); //Reply表格查詢特定客戶留言+客服回覆用
}
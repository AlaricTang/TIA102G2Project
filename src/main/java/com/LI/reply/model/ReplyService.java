package com.LI.reply.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.LI.reply.model.ReplyVO;

@Service("replyService")
public class ReplyService {

	@Autowired
	ReplyRepository repository;

	public void addReply(ReplyVO replyVO) {
		repository.save(replyVO);
	}

//	public void updateReply(ReplyVO replyVO) {
//		repository.save(replyVO);
//	}

//	public void deleteReply(Integer replyID) {
//		if (repository.existsById(replyID))
//			repository.deleteById(replyID);
//	}


	public ReplyVO getOneReply(Integer replyID) {
		Optional<ReplyVO> optional = repository.findById(replyID);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

//
//	public List<ReplyVO> getAll() {
//		return repository.findAll();
//	}
//
//	不用複合查詢
//	public Set<ReplyVO> getEmpsByDeptno(Integer deptno) {
//		return getOneReply(replyID).getReplys();
//	}

}

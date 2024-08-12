package com.LI.reply.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("replyService")
public class ReplyService {

	@Autowired
	ReplyRepository repository;
	
	//Reply表格查詢特定客戶留言+客服回覆用
	@Autowired
    private ReplyRepository replyRepository;

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


	//Reply表格查詢特定客戶留言+客服回覆用
    public List<ReplyVO> getRepliesByCustomerID(int customerID) {
        return replyRepository.findByCustomerID(customerID);
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

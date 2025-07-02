package com.service;

import java.util.List;
import java.util.Optional;

import com.entity.JibeiOrderDetailVO;
import com.repository.JibeiOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jibeiOrderDetailService")
public class JibeiOrderDetailService {

	@Autowired
	JibeiOrderDetailRepository repository;
	
	
	public void addJibeiOrderDetail(JibeiOrderDetailVO jibeiOrderDetailVO) {
		repository.save(jibeiOrderDetailVO);
	}
	
	public void updateJibeiOrderDetail(JibeiOrderDetailVO jibeiOrderDetailVO) {
		repository.save(jibeiOrderDetailVO);
	}
	
	public void deleteJibeiOrderDetail(Integer jibeiOrderDetailID) {
		if(repository.existsById(jibeiOrderDetailID))
			repository.deleteByJibeiOrderDetailID(jibeiOrderDetailID);
	}
	
	public JibeiOrderDetailVO getOneJibeiOrderDetail (Integer jibeiOrderDetailID) {
		Optional<JibeiOrderDetailVO> optional = repository.findById(jibeiOrderDetailID);
		return optional.orElse(null);
	}
	
	public List<JibeiOrderDetailVO> getAll(){
		return repository.findAll();
	}
	
	public List<JibeiOrderDetailVO> getByProductOrderID(Integer productOrderID){
		return repository.getByProductOrderID(productOrderID);
	}
	
}

package com.ken.cup.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_Cup;

@Service("cupService")
public class CupService {
	
	@Autowired
	CupRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addCup(CupVO cupVO) {
		repository.save(cupVO);
	}
	
	
	public void updateCup(CupVO cupVO) {
		repository.save(cupVO);
	}
	
	public void deleteCup(Integer cupID) {
		if (repository.existsById(cupID))
			repository.deleteByCupID(cupID);
//		    repository.deleteById(empno);
	}
	
	public CupVO getOneCup(Integer cupID) {
		Optional<CupVO> optional = repository.findById(cupID);
		return optional.orElse(null);
	}
	
	public List<CupVO> getAll(){
		return repository.findAll();
	}
	
	public List<CupVO> getAll(Map<String, String[]> map){
		return CompositeQuery_Cup.getAllC(map,sessionFactory.openSession());
	}
	
	// 自訂查詢 1 查詢該店家有多少可以出租的杯子
	public Long countCupsByStoreAndStatus(Integer storeID) {
        return repository.countByStoreIDAndCupStatus(storeID);
    }
	
	// 自訂查詢 2 查詢該店家有多少杯子已經被出租
	
	
	// 使用者可以看到自己租的杯子
	 public List<CupVO> getRentedCupsByUser(Integer userID) {
	        return repository.findByUserIDAndCupStatus(userID, 1); // 1 表示租借中
	    }
}

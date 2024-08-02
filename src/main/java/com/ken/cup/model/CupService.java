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
}

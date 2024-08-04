package com.ken.cupRecord.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_CupRecord;

@Service("cupRecordService")
public class CupRecordService {

	@Autowired
	CupRecordRepository repository;
	
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addCupRecord(CupRecordVO cupRecordVO) {
		repository.save(cupRecordVO);
	}
	
	// 雖然記錄相關應該不會去動到 不過還是留著
	public void updateCupRecord(CupRecordVO cupRecordVO) {
		repository.save(cupRecordVO);
	}
	
	// 刪除功能先保留
	public void deleteCupRecord(Integer cupRecordID) {
		if (repository.existsById(cupRecordID))
			repository.deleteByCupReCord(cupRecordID);
	}
	
	public CupRecordVO getOneCupRecord(Integer cupRecordID) {
		Optional<CupRecordVO> optional = repository.findById(cupRecordID);
		
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	public List<CupRecordVO> getAll(){
		return repository.findAll();
	}
	
	public List<CupRecordVO> getAll(Map<String, String[]> map){
		return CompositeQuery_CupRecord.getAllC(map, sessionFactory.openSession());
	}
}

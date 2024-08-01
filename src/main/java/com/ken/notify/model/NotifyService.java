package com.ken.notify.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("notifyService")
public class NotifyService {

	@Autowired
	NotifyRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addNotify(NotifyVO notifyVO) {
		repository.save(notifyVO);
	}
	
	// 我在想之後要不要多一個欄位是狀態
	public void updateNotify(NotifyVO notifyVO) {
		repository.save(notifyVO);
	}
	
	public List<NotifyVO> findByUserIDOrderByNotifyTimeDesc(int userID){
		return repository.findByUserIDOrderByNotifyTimeDesc(userID);
	}
	
	public NotifyVO getOneNotify(Integer NotifyID) {
		Optional<NotifyVO> optional = repository.findById(NotifyID);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	public List<NotifyVO> getALL(){
		return repository.findAll();
	}
}

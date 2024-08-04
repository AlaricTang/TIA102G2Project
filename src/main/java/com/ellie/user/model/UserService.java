package com.ellie.user.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.util.compositeQuery.CompositeQuery_User;

@Service("userService")
public class UserService {

	@Autowired
	UserRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(UserVO userVO) {
		repository.save(userVO);
	}

	public void updateUser(UserVO userVO) {
		repository.save(userVO);
	}

	public void deleteUser(Integer userId) {
		if (repository.existsById(userId))
			repository.deleteByUserID(userId);
	}

	public UserVO getOneUser(Integer userId) {
		Optional<UserVO> optional = repository.findById(userId);
		return optional.orElse(null);
	}

	public List<UserVO> getAll() {
		return repository.findAll();
	}
	
	public List<UserVO> getAll(Map<String, String[]> map) {
		return CompositeQuery_User.getAllC(map,sessionFactory.openSession());
		
	}
}

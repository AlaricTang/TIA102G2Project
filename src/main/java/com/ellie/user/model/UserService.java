package com.ellie.user.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

	public void deleteUser(Integer userID) {
		if (repository.existsById(userID))
			repository.deleteByUserID(userID);
	}

	public UserVO getOneUser(Integer userID) {
		Optional<UserVO> optional = repository.findById(userID);
		return optional.orElse(null);
	}

	public List<UserVO> getAll() {
		return repository.findAll();
	}
	
//	public List<UserVO> getAll(Map<String, String[]> map) {
//		return CompositeQuery_User.getAllC(map,sessionFactory.openSession());
//		
//	}

	public UserVO findByEmail(String email) {
        return repository.findByUserEmail(email);
    }

    public UserVO findById(Integer id) {
        Optional<UserVO> optional = repository.findById(id);
        return optional.orElse(null); 
    }
}


	


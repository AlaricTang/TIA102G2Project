package com.redis.userJibei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.redis.JedisService;

@Service
public class UserJibeiService {

	@Autowired
	JedisService jedisSvc;
	
	@Autowired
	private Gson gson;
	
	private static final String UserJibei_PREFIX="userJibei:";
	
	
	public void addUserJibei(Integer userId, UserJibeiVO userJibeiVO) throws IOException {
		String userJibeikey = UserJibei_PREFIX + userId;
		List<UserJibeiVO> userJibeiList  = new ArrayList<>();
		boolean itemExists = false;

		//取出 購物車裡的所有飲品 <json, json, json, json... >
		List<Object> userJibeiJsonList = jedisSvc.getItemsFromList(userJibeikey);

		//針對每個 json
		for (Object jsonString : userJibeiJsonList) {
			//每個json 都轉成VO    
			UserJibeiVO existingCartItem = gson.fromJson(jsonString.toString(), UserJibeiVO.class);
			//針對每個VO的drinkID 比對要加入的VO的drinkID
				//有找到: 原VO更新	加到List<VO>
				//沒找到: 原VO不更新	加到List<VO>
			if (existingCartItem.getDrinkID().equals(userJibeiVO.getDrinkID())) {
				
				existingCartItem.setNumber(
						existingCartItem.getNumber() + userJibeiVO.getNumber());// 更新数量
				userJibeiList.add(existingCartItem);
				itemExists = true;
			}else {
				userJibeiList.add(existingCartItem);
			}
		}
		
		//沒找到: 新VO 加到List<VO>
		if (!itemExists) {
			userJibeiList.add(userJibeiVO);
		} 
		//刪 舊購物車
		jedisSvc.delete(userJibeikey);
		//List<VO> 存進購物車
		for (UserJibeiVO item : userJibeiList) {
	        jedisSvc.saveItemToList(userJibeikey, gson.toJson(item));
	    }
	}
	
}


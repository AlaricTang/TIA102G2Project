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
	
	//======= 加入購物車 ======
	public void addUserJibei(Integer userID, UserJibeiVO userJibeiVO) throws IOException {
		String userJibeikey = UserJibei_PREFIX + userID;
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
	
	//======= 取出 所有會員寄杯 object轉成VO =======
	public List<UserJibeiVO> getUserJibei (Integer userID) throws IOException  {
		String userJibeikey = UserJibei_PREFIX + userID.toString();
		List<UserJibeiVO> userJibeiList  = new ArrayList<>();
		List<Object> userJibeJsonList  = jedisSvc.getItemsFromList(userJibeikey);
		for(Object userJibeJson : userJibeJsonList ) {
			userJibeiList.add(gson.fromJson(userJibeJson.toString(), UserJibeiVO.class));
		}
		return userJibeiList ;
	}
	
	//======= 兌換(成功or失敗) 某項商品 =======
	public boolean redeemUserJibei(Integer userID,Integer drinkID, Integer number) throws IOException {
		String userJibeikey = UserJibei_PREFIX + userID;
		List<UserJibeiVO> userJibeiList  = new ArrayList<>();
		boolean itemExists = false;

		//取出 購物車裡的所有飲品 <json, json, json, json... >
		List<Object> userJibeiJsonList = jedisSvc.getItemsFromList(userJibeikey);

		//針對每個 json 都轉成VO
		for (Object jsonString : userJibeiJsonList) {
			UserJibeiVO existingUserJibei = gson.fromJson(jsonString.toString(), UserJibeiVO.class);
			//針對每個VO的drinkID 比對要加入的drinkID
				//有找到: 原VO更新	加到List<VO>
				//沒找到: 原VO不更新
			
			//有找到 兌換( 減數量(存進要更新的List) or刪除)
			if(existingUserJibei.getDrinkID().equals(drinkID)) {
				
				if (existingUserJibei.getNumber()> number) {
					existingUserJibei.setNumber(existingUserJibei.getNumber() - number);// 更新数量
					userJibeiList.add(existingUserJibei);
					itemExists = true;
				}else if(existingUserJibei.getNumber() == number) {
					removeUserJibei(userID, drinkID);
					itemExists = true;
				}else{
					return false;
				}
			//沒找到 存進要更新的List
			}else {
				userJibeiList.add(existingUserJibei);				
			}
		}
		
		//有找到 更新 回傳true
		if(itemExists) {
			//刪 List 存新List
			jedisSvc.delete(userJibeikey);
			for (UserJibeiVO item : userJibeiList) {
				jedisSvc.saveItemToList(userJibeikey, gson.toJson(item));
			}
			return true;
		}
		//沒找到 回傳false
		return false;
	}
		
	
	
	
	//====== 刪掉 某項寄杯 ======
	public void removeUserJibei (Integer userID,Integer drinkID) throws IOException{
		String userJibeikey = UserJibei_PREFIX + userID;
		List<Object> userJibeJsonList = jedisSvc.getItemsFromList(userJibeikey);
        for (Object userJibeJson : userJibeJsonList) {
        	UserJibeiVO userJibei = gson.fromJson(userJibeJson.toString(), UserJibeiVO.class);
            if (userJibei.getDrinkID().equals(drinkID)) {
            	jedisSvc.removeItemFromList(userJibeikey, userJibei);
                break;
            }
        }
	}
	
	//====== 刪掉 所有會員寄杯 ======
	public void deleteDrinkCart(Integer userID)throws IOException{
		String userJibeikey = UserJibei_PREFIX + userID;
		jedisSvc.delete(userJibeikey);
	}
	
	
}


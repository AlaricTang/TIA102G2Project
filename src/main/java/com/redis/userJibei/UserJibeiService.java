//package com.redis.userJibei;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.google.gson.Gson;
//import com.redis.JedisService;
//import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;
//
//@Service
//public class UserJibeiService {
//
//	@Autowired
//	JedisService jedisSvc;
//	
//	@Autowired
//	private Gson gson;
//	
//	private static final String UserJibei_PREFIX="userJibei:";
//	
//	
//	public void addUserJibei(Integer userId, DrinkOrderDetailVO cartItem) throws IOException {
//		String cartKey = UserJibei_PREFIX + userId;
//		List<DrinkOrderDetailVO> cartItems  = new ArrayList<>();
//		boolean itemExists = false;
//
//		//取出 購物車裡的所有飲品 <json, json, json, json...>
//		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
//
//		//針對每個 json
//		for (Object jsonString : cartJsonList) {
//			//每個json 都轉成VO    
//			DrinkOrderDetailVO existingCartItem = gson.fromJson(jsonString.toString(), DrinkOrderDetailVO.class);
//			//針對每個VO的drinkID 比對要加入的VO的drinkID
//				//有找到: 原VO更新	加到List<VO>
//				//沒找到: 原VO不更新	加到List<VO>
//			if (existingCartItem.getDrinkID().equals(cartItem.getDrinkID())) {
//				
//				existingCartItem.setDrinkOrderDetailAmount(
//						existingCartItem.getDrinkOrderDetailAmount() + cartItem.getDrinkOrderDetailAmount());// 更新数量
//				cartItems.add(existingCartItem);
//				itemExists = true;
//			}else {
//				cartItems.add(existingCartItem);
//			}
//		}
//		//沒找到: 新VO 加到List<VO>
//		if (!itemExists) {
//			cartItems.add(cartItem);
//		} 
//		//刪 舊購物車
//		jedisSvc.delete(cartKey);
//		//List<VO> 存進購物車
//		for (DrinkOrderDetailVO item : cartItems) {
//	        jedisSvc.saveItemToList(cartKey, gson.toJson(item));
//	    }
//	}
//	
//}
//

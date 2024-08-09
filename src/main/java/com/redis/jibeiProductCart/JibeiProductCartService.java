package com.redis.jibeiProductCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.redis.JedisService;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;

@Service
public class JibeiProductCartService {

	@Autowired
	JedisService jedisSvc;
	
	@Autowired
	private Gson gson;
	
	private static final String JIBEIPRODUCTCART_PREFIX = "drinkCart:";

	public void addCartItem(Integer userId, JibeiOrderDetailVO cartItem) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userId;
		List<JibeiOrderDetailVO> cartItems  = new ArrayList<>();
		boolean itemExists = false;

		//取出 購物車裡的所有商品 <json, json, json, json...>
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
		
		//針對每個json
		for (Object jsonString : cartJsonList) {
			//每個json 都轉成VO 
			JibeiOrderDetailVO existingCartItem = gson.fromJson(jsonString.toString(), JibeiOrderDetailVO.class);
			//針對每個VO的drinkID 比對要加入的VO的drinkID
				//有找到: 原VO更新	加到List<VO>
				//沒找到: 原VO不更新	加到List<VO>
			if (existingCartItem.getJibeiProductID().equals(cartItem.getJibeiProductID())) {
				
				existingCartItem.setJibeiOrderDetailAmount(
						existingCartItem.getJibeiOrderDetailAmount() + cartItem.getJibeiOrderDetailAmount()); // 更新数量
				cartItems.add(existingCartItem);
				itemExists = true;
			}else {
				cartItems.add(existingCartItem);
			}
		}
		//沒找到: 新VO 加到List<VO>
		if (!itemExists) {
			cartItems.add(cartItem);
		} 
		//刪 舊購物車
		jedisSvc.delete(cartKey);
		//List<VO> 存進購物車
		for (JibeiOrderDetailVO item : cartItems) {
            jedisSvc.saveItemToList(cartKey, gson.toJson(item));
        }
	}

	// 取出購物車 object轉成VO
	public List<JibeiOrderDetailVO> getJibeiProductCart(Integer userID) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userID.toString();
		List<JibeiOrderDetailVO> jibeiProductCartItems = new ArrayList<>();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
		for (Object cartJson : cartJsonList) {
			jibeiProductCartItems.add(gson.fromJson(cartJson.toString(), JibeiOrderDetailVO.class));
		}
		return jibeiProductCartItems;
	}

	public void removeJibeiProductCartItem(Integer userID, Integer jibeiProductID) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userID.toString();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
		for (Object cartJson : cartJsonList) {
			JibeiOrderDetailVO jibeiOrderDetail = gson.fromJson(cartJson.toString(), JibeiOrderDetailVO.class);
			if (jibeiOrderDetail.getJibeiProductID().equals(jibeiProductID)) {
				jedisSvc.removeItemFromList(cartKey, cartJson);
				break;
			}
		}
	}

	public void deleteJibeiProductCart(Integer userID) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userID.toString();
		jedisSvc.delete(cartKey);
	}

}

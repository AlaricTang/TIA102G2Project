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

	public void addCartItem(String userId, JibeiOrderDetailVO cartItem) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userId;
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
		boolean itemExists = false;

		List<JibeiOrderDetailVO> cartItems  = new ArrayList<>();
		
		for (Object jsonString : cartJsonList) {
			JibeiOrderDetailVO existingCartItem = gson.fromJson(jsonString.toString(), JibeiOrderDetailVO.class);

			if (existingCartItem.getJibeiProductID().equals(cartItem.getJibeiProductID())) {
				
				existingCartItem.setJibeiOrderDetailAmount(
						existingCartItem.getJibeiOrderDetailAmount() + cartItem.getJibeiOrderDetailAmount()); // 更新数量
				cartItems.add(existingCartItem);
				itemExists = true;
			}else {
				cartItems.add(existingCartItem);
			}
		}

		if (!itemExists) {
			cartItems.add(cartItem);
		} 
		jedisSvc.delete(cartKey);
		
		for (JibeiOrderDetailVO item : cartItems) {
            jedisSvc.saveItemToList(cartKey, gson.toJson(item));
        }
	}

	// 取出購物車 ob轉成VO
	public List<JibeiOrderDetailVO> getJibeiProductCart(Integer userID) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userID.toString();
		List<JibeiOrderDetailVO> jibeiProductCartItems = new ArrayList<>();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
		for (Object cartJson : cartJsonList) {
			jibeiProductCartItems.add(gson.fromJson(cartJson.toString(), JibeiOrderDetailVO.class));
		}
		return jibeiProductCartItems;
	}

	public void removeJibeiProductCartItem(Integer userID, Integer drinkID) throws IOException {
		String cartKey = JIBEIPRODUCTCART_PREFIX + userID.toString();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
		for (Object cartJson : cartJsonList) {
			JibeiOrderDetailVO jibeiOrderDetail = gson.fromJson(cartJson.toString(), JibeiOrderDetailVO.class);
			if (jibeiOrderDetail.getJibeiOrderDetailID().equals(drinkID)) {
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

package com.redis.drinkCartTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.redis.JedisService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Service
public class DrinkCartService {
	
	@Autowired
	JedisService jedisSvc;
	
	@Autowired
	private Gson gson;
	
	private static final String DRINKCART_PREFIX="drinkCart:";
	private static final String DRINKORDER_PREFIX="drinkOrder:";
	
	//======= 加入購物車 ======
	public void addDrinkCartItem(Integer userId, DrinkOrderDetailVO cartItem) throws IOException {
		String cartKey = DRINKCART_PREFIX + userId;
		List<DrinkOrderDetailVO> cartItems  = new ArrayList<>();
		boolean itemExists = false;

		//取出 購物車裡的所有飲品 <json, json, json, json...>
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);

		//針對每個 json
		for (Object jsonString : cartJsonList) {
			//每個json 都轉成VO    
			DrinkOrderDetailVO existingCartItem = gson.fromJson(jsonString.toString(), DrinkOrderDetailVO.class);
			//針對每個VO的drinkID 比對要加入的VO的drinkID
				//有找到: 原VO更新	加到List<VO>
				//沒找到: 原VO不更新	加到List<VO>
			if (existingCartItem.getDrinkID().equals(cartItem.getDrinkID())) {
				
				existingCartItem.setDrinkOrderDetailAmount(
						existingCartItem.getDrinkOrderDetailAmount() + cartItem.getDrinkOrderDetailAmount());// 更新数量
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
		for (DrinkOrderDetailVO item : cartItems) {
            jedisSvc.saveItemToList(cartKey, gson.toJson(item));
        }
	}

	//======= 取出購物車 object轉成VO =======
	public List<DrinkOrderDetailVO> getDrinkCart (Integer userID) throws IOException  {
		String cartKey = DRINKCART_PREFIX + userID.toString();
		List<DrinkOrderDetailVO> drinkCartItems  = new ArrayList<>();
		List<Object> cartJsonList  = jedisSvc.getItemsFromList(cartKey);
		for(Object cartJson : cartJsonList ) {
			drinkCartItems.add(gson.fromJson(cartJson.toString(), DrinkOrderDetailVO.class));
		}
		return drinkCartItems ;
	}
	
	//====== 刪掉某項商品 ======
	public void removeDrinkCartItem (Integer userID,Integer drinkID) throws IOException{
		String cartKey = DRINKCART_PREFIX + userID.toString();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
        for (Object cartJson : cartJsonList) {
        	DrinkOrderDetailVO drinkOrderDetail = gson.fromJson(cartJson.toString(), DrinkOrderDetailVO.class);
            if (drinkOrderDetail.getDrinkID().equals(drinkID)) {
            	jedisSvc.removeItemFromList(cartKey, drinkOrderDetail);
                break;
            }
        }
	}
	
	//====== 刪掉購物車 ======
	public void deleteDrinkCart(Integer userID)throws IOException{
		String cartKey = DRINKCART_PREFIX + userID.toString();
		jedisSvc.delete(cartKey);
	}
	
	
//	購物車 訂購人資訊用
	public void setOneDrinkOrder(Integer userID , String key, String value) throws IOException {
		String drinkOrderKey = DRINKORDER_PREFIX + userID.toString();
		jedisSvc.saveOneOneOne(drinkOrderKey,key, value);
	}
	
	public String getOneDrinkOrder(Integer userID , String key) throws IOException{
		String drinkOrderKey = DRINKORDER_PREFIX + userID.toString();
		return jedisSvc.getOneOneOne(drinkOrderKey,key);
	}
	
	public void deleteDrinkOrder(Integer userID)throws IOException{
		String drinkOrderKey = DRINKORDER_PREFIX + userID.toString();
		jedisSvc.delete(drinkOrderKey);
	}
	
}

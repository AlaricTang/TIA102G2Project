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

	
	public void addDrinkCartItem (Integer userID, DrinkOrderDetailVO cartItem) throws IOException{
		String cartKey = DRINKCART_PREFIX + userID.toString();
		jedisSvc.saveItemToList(cartKey, cartItem);
	}
	

	//取出購物車 ob轉成VO
	public List<DrinkOrderDetailVO> getDrinkCart (Integer userID) throws IOException  {
		String cartKey = DRINKCART_PREFIX + userID.toString();
		List<DrinkOrderDetailVO> drinkCartItems  = new ArrayList<>();
		List<Object> cartJsonList  = jedisSvc.getItemsFromList(cartKey);
		for(Object cartJson : cartJsonList ) {
			drinkCartItems.add(gson.fromJson(cartJson.toString(), DrinkOrderDetailVO.class));
		}
		return drinkCartItems ;
	}
	
	
	public void removeDrinkCartItem (Integer userID,Integer drinkID) throws IOException{
		String cartKey = DRINKCART_PREFIX + userID.toString();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
        for (Object cartJson : cartJsonList) {
        	DrinkOrderDetailVO drinkOrderDetail = gson.fromJson(cartJson.toString(), DrinkOrderDetailVO.class);
            if (drinkOrderDetail.getDrinkID().equals(drinkID)) {
            	jedisSvc.removeItemFromList(cartKey, cartJson);
                break;
            }
        }
	}
	
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

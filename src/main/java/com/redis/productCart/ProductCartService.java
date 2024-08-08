package com.redis.productCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.JedisService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Service
public class ProductCartService {

	
	@Autowired
	JedisService jedisSvc;
	
	private static final String PRODUCTCART_PREFIX="productCart:";
	private static final String PRODUCTORDER_PREFIX="productOrder:";

	
	
	
	
	public void saveProductDetail(Integer userID, ProductOrderDetailVO cartItem) throws IOException {
		String cartkey=PRODUCTCART_PREFIX + userID.toString();
		jedisSvc.saveItemToList(cartkey, cartItem);
	}
	
	
	
	public List<ProductOrderDetailVO> getProductCart (Integer userID) throws IOException{
		String cartkey=PRODUCTCART_PREFIX + userID.toString();
		List<ProductOrderDetailVO> productCartItems = new ArrayList<>();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartkey);
		for(Object cartJson : cartJsonList) {
			productCartItems .add(new ObjectMapper().readValue(cartJson.toString(), ProductOrderDetailVO.class));
		}
		return productCartItems;
	}
	
	public void removeDrinkCartItem (Integer userID,Integer drinkID) throws IOException{
		String cartKey = PRODUCTCART_PREFIX + userID.toString();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
        for (Object cartJson : cartJsonList) {
        	DrinkOrderDetailVO drinkOrderDetail = new ObjectMapper().readValue(cartJson.toString(), DrinkOrderDetailVO.class);
            if (drinkOrderDetail.getDrinkID().equals(drinkID)) {
            	jedisSvc.removeItemFromList(cartKey, cartJson);
                break;
            }
        }
	}
	
	public void deleteDrinkCart(Integer userID)throws IOException{
		String cartKey = PRODUCTCART_PREFIX + userID.toString();
		jedisSvc.delete(cartKey);
	}
	
	
	
//	購物車 訂購人資訊用
	public void setOneDrinkOrder(Integer userID , String key, String value) throws IOException {
		String drinkOrderKey = PRODUCTORDER_PREFIX + userID.toString();
		jedisSvc.saveOneOneOne(drinkOrderKey,key, value);
	}
	
	public String getOneDrinkOrder(Integer userID , String key) throws IOException{
		String drinkOrderKey = PRODUCTORDER_PREFIX + userID.toString();
		return jedisSvc.getOneOneOne(drinkOrderKey,key);
	}
	
	public void deleteDrinkOrder(Integer userID)throws IOException{
		String drinkOrderKey = PRODUCTORDER_PREFIX + userID.toString();
		jedisSvc.delete(drinkOrderKey);
	}
	
}

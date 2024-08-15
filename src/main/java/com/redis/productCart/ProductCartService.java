package com.redis.productCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.redis.JedisService;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Service
public class ProductCartService {

	@Autowired
	JedisService jedisSvc;
	
	@Autowired
	private Gson gson;
	
	private static final String PRODUCTCART_PREFIX="productCart:";
	private static final String PRODUCTORDER_PREFIX="productOrder:";

	public void addCartItem(String userId, ProductOrderDetailVO cartItem) throws IOException {
		String cartKey = PRODUCTCART_PREFIX + userId;
		List<ProductOrderDetailVO> cartItems  = new ArrayList<>();
		boolean itemExists = false;

		//取出 購物車裡的所有飲品 <json, json, json, json...>
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);

		//針對每個json
		for (Object jsonString : cartJsonList) {
			//每個json 都轉成VO    
			ProductOrderDetailVO existingCartItem = gson.fromJson(jsonString.toString(), ProductOrderDetailVO.class);
			//針對每個VO的drinkID 比對要加入的VO的drinkID
				//有找到: 原VO更新	加到List<VO>
				//沒找到: 原VO不更新	加到List<VO> 
			if (existingCartItem.getProductVO().getProductID().equals(cartItem.getProductVO().getProductID())) {
				
				existingCartItem.setProductOrderDetailAmount(
						existingCartItem.getProductOrderDetailAmount() + cartItem.getProductOrderDetailAmount());// 更新数量
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
		for (ProductOrderDetailVO item : cartItems) {
            jedisSvc.saveItemToList(cartKey, gson.toJson(item));
        }
	}
	
//	public void saveProductDetail(Integer userID, ProductOrderDetailVO cartItem) throws IOException {
//		String cartkey=PRODUCTCART_PREFIX + userID.toString();
//		jedisSvc.saveItemToList(cartkey, cartItem);
//	}
	
	
	// 取出購物車 List<Object>  then 轉成<ProductOrderDetailVO>
	public List<ProductOrderDetailVO> getProductCart (Integer userID) throws IOException{
		String cartkey=PRODUCTCART_PREFIX + userID.toString();
		List<ProductOrderDetailVO> productCartItems = new ArrayList<>();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartkey);
		for(Object cartJson : cartJsonList) {
			productCartItems .add(gson.fromJson(cartJson.toString(), ProductOrderDetailVO.class));
		}
		return productCartItems;
	}
	
	//移除購物車 某項商品
	public void removeProductCartItem (Integer userID,Integer productID) throws IOException{
		String cartKey = PRODUCTCART_PREFIX + userID.toString();
		List<Object> cartJsonList = jedisSvc.getItemsFromList(cartKey);
        for (Object cartJson : cartJsonList) {
        	ProductOrderDetailVO productOrderDetail = gson.fromJson(cartJson.toString(), ProductOrderDetailVO.class);
            if (productOrderDetail.getProductVO().getProductID().equals(productID)) {
            	jedisSvc.removeItemFromList(cartKey, cartJson);
                break;
            }
        }
	}
	
	//清空購物車  //<<<<<<<<<<<<<<<<<< 這邊方法名幫我改  ProductCart 因為我動了，你的controller會報錯 我就沒改了
	public void deleteProductCart(Integer userID)throws IOException{
		String cartKey = PRODUCTCART_PREFIX + userID.toString();
		jedisSvc.delete(cartKey);
	}
	
	
	
//	================== 訂購人資訊用 ===================
	
	//存進 某項購物人資訊
	public void setOneProductOrder(Integer userID , String key, String value) throws IOException {
		String productOrderKey = PRODUCTORDER_PREFIX + userID.toString();
		jedisSvc.saveOneOneOne(productOrderKey,key, value);
	}
	
	//取得 某項購物人資訊  //<<<<<<<<<<<<<<<<<< 這邊方法名幫我改 getOneProductOrder
	public String getOneProductOrder(Integer userID , String key) throws IOException{
		String productOrderKey = PRODUCTORDER_PREFIX + userID.toString();
		return jedisSvc.getOneOneOne(productOrderKey,key);
	}
	
	//清空 訂購人資訊 //<<<<<<<<<<<<<<<<<< 這邊方法名幫我改 deleteProductOrder
	public void deleteProductOrder(Integer userID)throws IOException{
		String productOrderKey = PRODUCTORDER_PREFIX + userID.toString();
		jedisSvc.delete(productOrderKey);
	}
	
}

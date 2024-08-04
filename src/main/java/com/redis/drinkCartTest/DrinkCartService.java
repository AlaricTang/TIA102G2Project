package com.redis.drinkCartTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.JedisService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Service
public class DrinkCartService {
	
	@Autowired
	JedisService jedisSvc;
	
	//加入購物車前 把東西包裝成 DrinkOrderDetailVO
	public  void saveDrinkDetail(Integer userID, DrinkOrderDetailVO drinkOrderDetail)throws IOException{
		jedisSvc.saveToList(userID.toString(), drinkOrderDetail);
	}
	
	public List<DrinkOrderDetailVO> getDrinkCart (Integer userID) throws IOException{
		List<DrinkOrderDetailVO> beDrinkOrderDetailList = new ArrayList<>();
		List<Object> obDrinkCart = jedisSvc.getList(userID.toString());
		for(Object obDrinkDetail : obDrinkCart) {
			DrinkOrderDetailVO drinkOrderDetail = (DrinkOrderDetailVO)obDrinkDetail;
			beDrinkOrderDetailList.add(drinkOrderDetail);
		}
		return beDrinkOrderDetailList;
	}
	
	public void deleteDrinkDetail (Integer userID) throws IOException{
		jedisSvc.delete(userID.toString());
	}
	
//	購物車 訂購人資訊用
	public void setDrinkOrder(Integer userID , String key, String String) throws IOException {
		jedisSvc.saveUserOneOne(userID.toString(),key, String);
	}
	
	public String getDrinkOrder(Integer userID , String key) throws IOException{
		return jedisSvc.getUserOneOne(userID.toString(),key);
	}
	
}

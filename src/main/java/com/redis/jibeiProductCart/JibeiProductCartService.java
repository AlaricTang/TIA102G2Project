package com.redis.jibeiProductCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.JedisService;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;

@Service
public class JibeiProductCartService {

	@Autowired
	JedisService jedisSvc;
	
	//加入購物車 
	public void saveToCart(Integer userID, List<JibeiOrderDetailVO> cartList) throws IOException {
		jedisSvc.saveToList(userID.toString()+"JibeiProductDetail", cartList);
	}
	
	//取出購物車 ob轉成VO
	public List<JibeiOrderDetailVO> getJibeiProductCart(Integer userID) throws IOException{
		List<JibeiOrderDetailVO> beJibeiOrderDetailList = new ArrayList<>();
		
		List<Object> obJibeiProductCart = jedisSvc.getList(userID.toString()+"JibeiProductDetail");
		for(Object obJibeiOrderDetail : obJibeiProductCart) {
			JibeiOrderDetailVO drinkOrderDetail = (JibeiOrderDetailVO)obJibeiOrderDetail;
			beJibeiOrderDetailList.add(drinkOrderDetail);
		}
		return beJibeiOrderDetailList;
	}
	
	
	public void deleteDrinkDetail(Integer userID)throws IOException{
		jedisSvc.delete(userID.toString()+"JibeiProductDetail");
	}
	
	
}

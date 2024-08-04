package com.redis.productCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.JedisService;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Service
public class ProductCartService {

	
	@Autowired
	JedisService jedisSvc;
	
	public void saveProductDetail(Integer userID, ProductOrderDetailVO productOrderDetail) throws IOException {
		jedisSvc.saveToList(userID.toString(), productOrderDetail);
	}
	
	public List<ProductOrderDetailVO> getProductCart (Integer userID) throws IOException{
		List<ProductOrderDetailVO> beProductOrderDetailList = new ArrayList<>();
		List<Object> obProductCart = jedisSvc.getList(userID.toString());
		for(Object obProductDetail : obProductCart) {
			ProductOrderDetailVO productOrderDetail = (ProductOrderDetailVO)obProductDetail;
			beProductOrderDetailList.add(productOrderDetail);
		}
		return beProductOrderDetailList;
	}
	
	public void deleteProductDetail (Integer userID) throws IOException{
		jedisSvc.delete(userID.toString());
//		jedisSvc.deleteList(userID.toString());
	}
	
	public void setProductOrder(String key, String string) throws IOException{
		jedisSvc.saveOneOne(key, string);
	}
	
	public String getProductOrder(String key) throws IOException{
		return jedisSvc.getOneOne(key);
	}
	
}

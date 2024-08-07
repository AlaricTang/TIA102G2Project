package com.tang.jibeiProduct.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.user.model.UserVO;
import com.redis.jibeiProductCart.JibeiProductCartService;
import com.tang.jibeiProduct.model.JibeiProductService;
import com.tang.jibeiProduct.model.JibeiProductVO;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;

@Controller
@RequestMapping("/jibeiProduct")
public class JibeiProductFrontController {
	
	@Autowired
	JibeiProductService jibeiProductSvc;
	
	@Autowired
	JibeiProductCartService jibeiProductCartSvc;
	
	//取得所有 上架中 寄杯商品 
	@GetMapping("jibeiProductList")
	public String jibeiProductList(ModelMap model) {
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();
		model.addAttribute("jibeiProductList",onList);
		return "back-end/product/listAllProduct";
	}
	
	//取得一商品詳細頁面
	@PostMapping("jibeiPdDetail")
	public String jibeiPdDetail(
			@RequestParam("jibeiProductID") String jibeiProductID,
			ModelMap model) {
	
		JibeiProductVO jibeiProduct = jibeiProductSvc.getOneJibeiProduct(Integer.valueOf(jibeiProductID));
		model.addAttribute("jibeiProduct",jibeiProduct);
		return "back-end/jibeiProduct/singleJibeiProduct";
	}
	
	//加入購物車
	@PostMapping("addToCart")
	public String addToCart(
			@RequestParam("jibeiProductID") String jibeiProductID,
			@RequestParam("orderAmount") String orderAmount,
			HttpSession session) throws IOException {
		
		JibeiOrderDetailVO jibeiProductItem = new JibeiOrderDetailVO();
		jibeiProductItem.setJibeiProductID(Integer.valueOf(jibeiProductID));
		jibeiProductItem.setJibeiOrderDetailAmount(Integer.valueOf(orderAmount));
		
		UserVO user = (UserVO)session.getAttribute("user");
		
		
		List<JibeiOrderDetailVO> cartList = jibeiProductCartSvc.getJibeiProductCart(user.getUserId());
		
	    boolean itemExists = false;
	    for (JibeiOrderDetailVO item : cartList) {
	        if (item.getJibeiProductID() == jibeiProductItem.getJibeiProductID()) {
	            item.setJibeiOrderDetailAmount(item.getJibeiOrderDetailAmount() + jibeiProductItem.getJibeiOrderDetailAmount());
	            itemExists = true;
	            break;
	        }
	    }

	    if (!itemExists) {
	        cartList.add(jibeiProductItem);
	    }
		
		jibeiProductCartSvc.saveToCart(user.getUserId(),cartList);
		
		
		return "back-end/jibeiProduct/singleJibeiProduct";
	}
}

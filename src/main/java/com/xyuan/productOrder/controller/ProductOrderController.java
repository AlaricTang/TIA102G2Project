package com.xyuan.productOrder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ellie.user.model.UserVO;
import com.redis.drinkCartTest.DrinkCartService;
import com.redis.productCart.ProductCartService;
import com.xyuan.productOrder.model.ProductOrderService;
import com.xyuan.productOrder.model.ProductOrderVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Controller
@RequestMapping("/productOrder")
public class ProductOrderController {

	@Autowired
	ProductOrderService productOrderSvc;
	
	@Autowired
	ProductCartService productCartService; 
	
	
	//取得所有訂單列表
	@GetMapping("productOrderHistory")
	public String getAllProductOrders(ModelMap model) {
		List<ProductOrderVO> productOrders = productOrderSvc.getAll();
		model.addAttribute("productOrders", productOrders);
	        return "back-end/productOrder/productOrderHistory";
	    }
	

	
/* ------------------------------------------------------------------------------ */	
	
	// 新增訂單
	
	@GetMapping("productOrderPage")
	public String productOrderPage(ModelMap model, HttpSession session) throws IOException {
		ProductOrderVO productOrderVO = new ProductOrderVO();
		List<ProductOrderDetailVO> productCartList = new ArrayList<>();
		
//		訂購人
		UserVO user = (UserVO)session.getAttribute("user");
		Integer userID = user.getUserId();
		productOrderVO.setUserID(userID);
		
//		收件者姓名
		String str_receiverName = productCartService.getProductOrder("str_receiverName");
		productOrderVO.setReceiverName(str_receiverName);
		
//		收件者電話
		String str_receiverPhone = productCartService.getProductOrder("str_receiverPhone");
		productOrderVO.setReceiverPhone(str_receiverPhone);
		
//		收件者信箱
		String str_receiverMail = productCartService.getProductOrder("str_receiverMail");
		productOrderVO.setReceiverMail(str_receiverMail);
		
		
//		付款方式byte
		String str_productOrderPayM = productCartService.getProductOrder("str_productOrderPayM");
		productOrderVO.setProductOrderPayM(Byte.valueOf(str_productOrderPayM));
		
//		備註
		String str_productOrderNote = productCartService.getProductOrder("str_productOrderNote");
		productOrderVO.setProductOrderNote(str_productOrderNote);
		
		
//		訂單金額
		String str_productOrderAmount = productCartService.getProductOrder("productOrderAmount");
		productOrderVO.setProductOrderAmount(Integer.valueOf(str_productOrderAmount));
		
		
		
		session.setAttribute("productOrderVO", productOrderVO);
		
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("receiverName", str_receiverName);
		model.addAttribute("receiverPhone", str_receiverPhone);
		model.addAttribute("receiverMail", str_receiverMail);
		model.addAttribute("productOrderPayM", str_productOrderPayM);
		model.addAttribute("productOrderNote", str_productOrderPayM);
		model.addAttribute("str_productOrderAmount", str_productOrderAmount);
		
		productCartList = productCartService.getProductCart(userID);
		if(productCartList == null) {
			return "購物車頁面";
		}
		model.addAttribute("productCartList", productCartList);
		return "back-end/productOrder/productOrderPage";
		
	}
	
		@PostMapping("productOrderSuccess")
		public String productOrderSuccess(ModelMap model,HttpSession session)throws IOException{
			
			ProductOrderVO productOrderVO = (ProductOrderVO)session.getAttribute("productOrder");
			
			
	/////////先空著	/////////
					
			return null;
		}
	

	
	
	// 修改訂單
	
	// 透過複合查詢列出部份or全部訂單
	

}

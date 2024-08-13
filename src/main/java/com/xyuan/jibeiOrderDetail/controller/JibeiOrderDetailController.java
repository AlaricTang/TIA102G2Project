package com.xyuan.jibeiOrderDetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailService;
import com.xyuan.productOrder.model.ProductOrderService;

@Controller
@RequestMapping("/jibeiOrderDetail")
public class JibeiOrderDetailController {
	
	@Autowired
	JibeiOrderDetailService jibeiOrderDetailSvc;
								//JibeiOrderDetailSvc
	@Autowired
	ProductOrderService productOrderSvc;
						//productOrderSvc
	
//	//會員查訂單明細
//	@GetMapping("userProductOrderDetail")
//	public String userProductOrderDetail(
//			@RequestParam("productOrderID") String productOrderID, ModelMap model, HttpSession session) {
//			
//		List<JibeiOrderDetailVO> jibeiOrderDetail = jibeiOrderDetailSvc.getByProductOrderID(Integer.valueOf(productOrderID));
//		model.addAttribute("jibeiOrderDetailDetail", jibeiOrderDetail);
//		
//		UserVO user = (UserVO)session.getAttribute("user");
//		List<ProductOrderVO> productOrederList = productOrderSvc.getAllUserProductOrder(user.getUserId());
//		model.addAttribute("productOrderList", productOrederList);
//		
//		return "back-end/productOrder/userProductOrder";	
//	}
	
//存訂單的動作 跟 存訂單明細同步 所以寫在drinkOrderController
//後臺 訂單紀錄 查詢明細
	
//	@GetMapping("orderHistory_ProductOrderDetail")
//	public String orderHistory_ProductOrderDetail(
//			@RequestParam("productOrderID") String productOrderID, ModelMap model) {
//			
//		List<JibeiOrderDetailVO> jibeiOrderDetail = jibeiOrderDetailSvc.getByProductOrderID(Integer.valueOf(productOrderID));
//		model.addAttribute("jibeiOrderDetailDetail", jibeiOrderDetail);
//		
//		List<ProductOrderVO> productOrderList = productOrderSvc.getAll();
//		model.addAttribute("productOrderList", productOrderList);
//		return "back-end/productOrder/orderHistory";	
//	}
	
//後臺 訂單管理 查詢明細
	
//	@GetMapping("orderManage_ProductOrderDetail")
//	public String orderManage_ProductOrderDetail(
//			@RequestParam("productOrderID") String productOrderID, ModelMap model) {
//			
//		List<JibeiOrderDetailVO> jibeiOrderDetail = jibeiOrderDetailSvc.getByProductOrderID(Integer.valueOf(productOrderID));
//		model.addAttribute("jibeiOrderDetailDetail", jibeiOrderDetail);
//		
//		List<ProductOrderVO> productOrderList = productOrderSvc.getAllUndone();
//		model.addAttribute("productOrderList", productOrderList);
//		
//		return "back-end/productOrder/orderManage";
//			
//	}
	
}

package com.xyuan.productOrder.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.redis.userJibei.UserJibeiService;
import com.redis.userJibei.UserJibeiVO;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.jibeiProduct.model.JibeiProductService;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailService;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;
import com.xyuan.productOrder.model.ProductOrderService;
import com.xyuan.productOrder.model.ProductOrderVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailService;


@Controller
@Validated
@RequestMapping("/productOrder")
public class ProductOrderBackController {

	@Autowired
	ProductOrderService productOrderService;
	
	@Autowired
	ProductOrderDetailService productOrderDetailService;
	
	@Autowired
	JibeiOrderDetailService jibeiOrderDetailSvc;
	
	@Autowired
	JibeiProductService jibeiProductSvc;
	
	@Autowired
	UserJibeiService userJibeiSvc;

/* --------------------總公司-------------------- */
	//======= 跳轉 訂單紀錄 =======
	@GetMapping("orderHistory")
	public String orderHistory(ModelMap model) {
		List<ProductOrderVO> productOrderList = productOrderService.getAll();		
		model.addAttribute("productOrderList",productOrderList);
		return "back-end/productOrder/orderHistory";
	}
	
	//======= 跳轉 訂單管理 =======
	@GetMapping("orderManage")
	public String orderManage(ModelMap model) {
		List<ProductOrderVO> productOrderList = productOrderService.getAllUndone();		
		model.addAttribute("productOrderList",productOrderList);
		return "back-end/productOrder/orderManage";
	}
	
	//======= 複合查詢 from 訂單紀錄 =======
	@PostMapping("getProductOrder")
	public String getProductOrder(
			@RequestParam("productOrderID") String productOrderID,
			@RequestParam("userID") String userID,
			@RequestParam("productOrderStartCreateTime") String productOrderStartCreateTime,
			@RequestParam("productOrderEndCreateTime") String productOrderEndCreateTime,
			@RequestParam("productOrderStatus") String productOrderStatus, ModelMap model) {
				
		Map<String, String> map = new HashMap<>();
		map.put("drinkOrderID", productOrderID);
		map.put("userID", userID);
		map.put("productOrderStartCreateTime", productOrderStartCreateTime);
		map.put("productOrderEndCreateTime", productOrderEndCreateTime);
		map.put("productOrderStatus", productOrderStatus);
		
		List<ProductOrderVO> productOrderList = productOrderService.getAll(map);
		
		//沒查到,error準備
		if(productOrderList.isEmpty()) {	
			List<ProductOrderVO> list = productOrderService.getAll();
			model.addAttribute("productOrderList", list);
			model.addAttribute("errorMessage", "查無此訂單");
		}
		
		model.addAttribute("productOrderList", productOrderList);
		return "back-end/productOrder/orderHistory";		
	}
	
	//======= 複合查詢 from 訂單管理 =======
	@PostMapping("getUndoneProductOrder")
	public String getUndoneProductOrder(
			@RequestParam("productOrderID") String productOrderID,
			@RequestParam("userID") String userID,
			@RequestParam("productOrderStartCreateTime") String productOrderStartCreateTime,
			@RequestParam("productOrderEndCreateTime") String productOrderEndCreateTime,
			ModelMap model) {
		
		Map<String, String> map = new HashMap<>();
		map.put("productOrderID", productOrderID);
		map.put("userID", userID);
		map.put("productOrderStartCreateTime", productOrderStartCreateTime);
		map.put("productOrderEndCreateTime", productOrderEndCreateTime);
		map.put("productOrderStatus", "0");//查的東西皆為"未完成" -- 超級酷
		
		List<ProductOrderVO> productOrderList = productOrderService.getAll(map);

		
		//查無此訂單
		if(productOrderList == null) {	
			List<ProductOrderVO> list = productOrderService.getAllUndone();
			model.addAttribute("productOrderList", list);
			model.addAttribute("errorMessage", "查無此訂單");
		}
				
		//傳給前端
		model.addAttribute("productOrderList", productOrderList);
		return "back-end/productOrder/orderManage";
		
	}
	
	//======= 完成 訂單(含付款)狀態 加入會員寄杯=======
	@PostMapping("successProductOrder")
	public String successDrinkOrder(@RequestParam("productOrderID") String productOrderID, ModelMap model) throws IOException {
		//狀態改變 存訂單
		ProductOrderVO productOrder = productOrderService.getOneProductOrder(Integer.valueOf(productOrderID));
		productOrder.setProductOrderStatus(Byte.valueOf("1"));
		productOrder.setProductOrderPayStatus(Byte.valueOf("1"));
		productOrderService.updateProductOrder(productOrder);
		
		
		//取訂單userID
		Integer userID = productOrderService.getOneProductOrder(Integer.valueOf(productOrderID)).getUserID();
		
		
		//取此訂單明細 去forEach 
		List<JibeiOrderDetailVO> beUserJibeiList = jibeiOrderDetailSvc.getByProductOrderID(Integer.valueOf(productOrderID));
		for(JibeiOrderDetailVO beUserJibei : beUserJibeiList ) {
			UserJibeiVO userJibei = new UserJibeiVO();
			
			//for(一個明細VO	: 這筆訂單的所有明細)
			//1. 一個明細VO >> 寄杯商品ID
			//2. 寄杯商品Svc(寄杯商品ID) >> 取得寄杯商品VO >> 飲品
			userJibei.setDrinkID(jibeiProductSvc.getOneJibeiProduct(beUserJibei.getJibeiProductID()).getDrinkID());
			userJibei.setNumber(beUserJibei.getJibeiOrderDetailAmount());
			
			//存進User寄杯
			userJibeiSvc.addUserJibei(userID, userJibei);
		}
		return "redirect:/productOrder/orderHistory";
	}
	
	//======= 完成 付款狀態 =======
	@PostMapping("sussesPaidProductOrder")
	public String sussesPaidProductOrder(@RequestParam("productOrderID") String productOrderID, ModelMap model) {
		ProductOrderVO productOrder = productOrderService.getOneProductOrder(Integer.valueOf(productOrderID));
		productOrder.setProductOrderPayStatus(Byte.valueOf("1"));
		productOrderService.updateProductOrder(productOrder);
		return "redirect:/productOrder/orderManage";
	}
	
	//======= 取消 訂單狀態 =======
	@PostMapping("cancelProductOrder")
	public String cancelDrinkOrder(@RequestParam("productOrderID") String productOrderID, ModelMap model) {
		ProductOrderVO productOrder = productOrderService.getOneProductOrder(Integer.valueOf(productOrderID));
		productOrder.setProductOrderStatus(Byte.valueOf("2"));
		productOrderService.updateProductOrder(productOrder);
		return "redirect:/productOrder/orderHistory";
	}
/* -------------------- 店家 -------------------- */

	//商品的店家端只有寄杯商品	
	
	
	
	
}

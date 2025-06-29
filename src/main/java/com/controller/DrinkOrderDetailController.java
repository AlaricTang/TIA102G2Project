package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.service.StoreService;
import com.entity.StoreVO;
import com.service.DrinkOrderService;
import com.entity.DrinkOrderVO;
import com.service.DrinkOrderDetailService;
import com.entity.DrinkOrderDetailVO;


//前後台寫一起
@Controller
@RequestMapping("/drinkOrderDetail")
public class DrinkOrderDetailController {
	
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;
	
	@Autowired
	DrinkOrderService drinkOrderSvc;

	@Autowired
	StoreService storeSvc;

//	會員查訂單明細
	@PostMapping("userDrinkOrderDetail")
	public String userDrinkOrderDetail(
			@RequestParam("drinkOrderID") String drinkOrderID,
			ModelMap model,HttpSession session,RedirectAttributes redirectAttributes) {
		
		List<DrinkOrderDetailVO> drinkOrderDetailList = drinkOrderDetailSvc.getByDrinkOrderID(Integer.valueOf(drinkOrderID));
		redirectAttributes.addAttribute("drinkOrderDetailList",drinkOrderDetailList);
		
//		UserVO user = (UserVO)session.getAttribute("user");
//		List<DrinkOrderVO> userDrinkOrderList = drinkOrderSvc.getAllUserDrinkOrder(user.getUserId());
//		model.addAttribute("userDrinkOrderList",userDrinkOrderList);
//		
		return "redirect:/drinkOrder/userDrinkOrder";
	}
	
	
//後臺 訂單紀錄 查詢明細
	@PostMapping("orderHistory_DrinkOrderDetail")
	public String orderHistory_DrinkOrderDetail(
			@RequestParam("drinkOrderID") String drinkOrderID,ModelMap model) {
		
		List<DrinkOrderDetailVO> drinkOrderDetailList = drinkOrderDetailSvc.getByDrinkOrderID(Integer.valueOf(drinkOrderID));
		model.addAttribute("drinkOrderDetailList",drinkOrderDetailList);
		
		List<DrinkOrderVO> drinkOrderList = drinkOrderSvc.getAll();
		model.addAttribute("drinkOrderList",drinkOrderList);
		
		List<StoreVO> storeList = storeSvc.getAll();
		model.addAttribute("storeList",storeList);
		return "back-end/drinkOrder/orderHistory";
	}
	
//後臺 訂單管理 查詢明細
	@PostMapping("orderManage_DrinkOrderDetail")
	public String orderManage_DrinkOrderDetail(
			@RequestParam("drinkOrderID") String drinkOrderID,ModelMap model) {
		
		List<DrinkOrderDetailVO> drinkOrderDetailList = drinkOrderDetailSvc.getByDrinkOrderID(Integer.valueOf(drinkOrderID));
		model.addAttribute("drinkOrderDetailList",drinkOrderDetailList);
		
		List<DrinkOrderVO> drinkOrderList = drinkOrderSvc.getAllUndone();
		model.addAttribute("drinkOrderList",drinkOrderList);
		
		List<StoreVO> storeList = storeSvc.getAll();
		model.addAttribute("storeList",storeList);
		return "back-end/drinkOrder/orderManage";
	}

	


	

}
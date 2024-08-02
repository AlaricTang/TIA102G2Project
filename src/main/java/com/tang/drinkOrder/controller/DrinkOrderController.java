package com.tang.drinkOrder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ellie.user.model.UserVO;
import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Controller
@RequestMapping("/drinkOrder")
public class DrinkOrderController {

	@Autowired
	DrinkOrderService drinkOrderSvc;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;

	//跳轉到 要下單的頁面
		//需要將購物人資訊  購物車物品列出來
	@GetMapping("drinkOrderPage")
	public String drinkOrderPage(ModelMap model, HttpSession session) {
		DrinkOrderVO drinkOrderVO = new DrinkOrderVO();
		List<DrinkOrderDetailVO> drinkOrderDetailList = new ArrayList<>();
		//session裡取得 會員名稱
		String userName = ((UserVO)session.getAttribute("user")).getUserName();
		model.addAttribute("userName",userName);
		
		
		
		model.addAttribute("drinkOrderVO",drinkOrderVO);
		model.addAttribute("drinkOrderDetailList",drinkOrderDetailList);
		return "back-end/drinkOrder/drinkOrderPage";
	}
	
	
	@PostMapping("orderSuccess")
	public String orderSuccess(@Valid DrinkOrderVO drinkOrderVO, BindingResult result
			,ModelMap model)throws IOException{
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		//綠界
		/*************************** 2.開始新增資料 *****************************************/
		
		DrinkOrderVO saveDrinkOrder = drinkOrderSvc.addAndGetDrinkOrder(drinkOrderVO);
//		drinkOrderDetailSvc.addDrinkOrderDetail();
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		model.addAttribute("saveDrinkOrder", saveDrinkOrder);
		return "redirect:/drinkOrder/orderSuccess";
	}
	
	
	
}

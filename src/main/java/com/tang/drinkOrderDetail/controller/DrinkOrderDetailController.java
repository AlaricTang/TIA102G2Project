package com.tang.drinkOrderDetail.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ellie.user.model.UserVO;
import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;


//前後台寫一起
@Controller
@RequestMapping("/drinkOrderDetail")
public class DrinkOrderDetailController {
	
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;
	
	@Autowired
	DrinkOrderService drinkOrderSvc;

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
		return "back-end/drinkOrder/orderManage";
	}

	


	

}
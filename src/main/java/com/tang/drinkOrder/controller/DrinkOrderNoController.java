package com.tang.drinkOrder.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Controller
@Validated
@RequestMapping("/drinkOrder")
public class DrinkOrderNoController {

	@Autowired
	DrinkOrderService drinkOrderService;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailService;

	//從訂單管理 或訂單紀錄來 原頁面存在location
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			@NotEmpty(message = "訂單編號:請勿空白")
			@Min(value= 0 , message = "訂單編號: 不可小於 {value}")
			@RequestParam("drinkOrderID") String drinkOrderID,
			@RequestParam("location") String location,
			ModelMap model) {
		
		//查訂單
		DrinkOrderVO drinkOrderVO = drinkOrderService.getOneDrinkOrder(Integer.valueOf(drinkOrderID));
	
		//沒查到,回原頁面 
		if(drinkOrderVO == null) {	
			List<DrinkOrderVO> list = drinkOrderService.getAll();
			model.addAttribute("drinkOrderListData", list);
			model.addAttribute("drinkOrderVO", new DrinkOrderVO());
			model.addAttribute("errorMessage", "查無此訂單");
			return location;
		}
		
		//查到,回原頁面
		model.addAttribute("drinkOrderVO", drinkOrderVO);
		model.addAttribute("getOne_For_Display", "true");
		
		return location;
	}
	
	//更改 訂單狀態 邏輯我要再想一下
	
	
	
	
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e,Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage()+"<br>");
		}
		
//		model.addAttribute("drinkOrderVO", new DrinkOrderVO());
//		DrinkOrderService drinkOrderSvc = new DrinkOrderService();
		List<DrinkOrderVO> list = drinkOrderService.getAll();
		model.addAttribute("drinkOrderListData", list);
		model.addAttribute("drinkOrderVO", new DrinkOrderVO());
		List<DrinkOrderDetailVO> list2 = drinkOrderDetailService.getAll();
		model.addAttribute("drinkOrderDetailListData", list2);
		String message = strBuilder.toString();
		return new ModelAndView("back-end/drinkOrder/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}
}

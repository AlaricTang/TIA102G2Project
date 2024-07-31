package com.tang.drinkOrder.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;

@Controller
@RequestMapping("/drinkOrder")
public class DrinkOrderController {

	@Autowired
	DrinkOrderService drinkOrderSvc;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;

	//跳轉到 要下單的頁面
	@GetMapping("drinkOrderPage")
	public String drinkOrderPage(ModelMap model) {
		DrinkOrderVO drinkOrderVO = new DrinkOrderVO();
		model.addAttribute("drinkOrderVO",drinkOrderVO);
		return "back-end/drinkOrder/drinkOrderPage";
	}
	
	
	@PostMapping("insert")
	public String insert(@Valid DrinkOrderVO drinkOrderVO, BindingResult result ,ModelMap model)throws IOException{
		
		return null;
	}
	
	
	
}

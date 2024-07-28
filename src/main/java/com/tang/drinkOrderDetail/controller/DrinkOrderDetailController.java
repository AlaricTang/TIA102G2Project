package com.tang.drinkOrderDetail.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Controller
@RequestMapping("/drinkOrderDetail")
public class DrinkOrderDetailController {
	
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;
	
	@Autowired
	DrinkOrderService drinkOrderSvc;

	@GetMapping("addDrinkOrderDetail") //測試用
	public String addDrinkOrderDetail(Model model) {
		DrinkOrderDetailVO drinkOrderDetailVO = new DrinkOrderDetailVO();
		model.addAttribute("drinkOrderDetailVO", drinkOrderDetailVO);
		return "back-end/user/drinkOrder"; //跳轉到會員訂單紀錄
	}

	@PostMapping("insertDrinkOrderDetail") //看能不能從drinkOrder再傳過來
	public String insertDrinkOrderDetail(@Valid DrinkOrderDetailVO drinkOrderDetailVO, BindingResult result, ModelMap model) throws IOException{
		drinkOrderDetailSvc.addDrinkOrderDetail(drinkOrderDetailVO);
		
		List<DrinkOrderVO> list = drinkOrderSvc.getAll();
		model.addAttribute("deinkOrderListData", list);
		model.addAttribute("","- (新增成功)");
		return "redirect:/deinkOrder/listAllMap";//到去哪再說 先去看所有訂單
	}
	

	

}
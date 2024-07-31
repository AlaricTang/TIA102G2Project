package com.tang.drinkOrderDetail.controller;


import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

import oracle.jdbc.proxy.annotation.Post;


@Controller
@RequestMapping("/drinkOrderDetail")
public class DrinkOrderDetaiINoController {
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;
	
	@Autowired
	DrinkOrderService drinkOrderSvc;
	
//	@GetMapping("addDrinkOrderDetail")
//	public String addDrinkOrderDetail(Model model) {
//		DrinkOrderDetailVO deinkOrderDetailVO = new DrinkOrderDetailVO();
//		model.addAttribute("deinkOrderDetailVO",deinkOrderDetailVO);
//		return null;
//	}
	
	
//	//統一前往drinkOrder Controller處理
//	@PostMapping("insert")
//	public String insert(@Valid DrinkOrderDetailVO drinkOrderDetailVO, BindingResult result, ModelMap model) throws IOException{
//		drinkOrderDetailSvc.addDrinkOrderDetail(drinkOrderDetailVO);
//		
//		return null;
//	}
	
	
	
	
	
	
	
	
	
//	
//	@ExceptionHandler(value = { ConstraintViolationException.class })
//	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
//	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//	    StringBuilder strBuilder = new StringBuilder();
//	    for (ConstraintViolation<?> violation : violations ) {
//	          strBuilder.append(violation.getMessage() + "<br>");
//	    }
//		List<DrinkOrderDetailVO> list = drinkOrderDetailSvc.getAll();
//		model.addAttribute("drinkOrderDetailListData", list);  // for select_page.jsp 第96 108行用
//		model.addAttribute("drinkOrderDetailVO", new DrinkOrderDetailVO()); // for select_page.jsp 第94 106行用
//		List<DrinkOrderVO> list2 = drinkOrderSvc.getAll();
//    	model.addAttribute("drinkOrderListData",list2); // for select_page.jsp 第135行用
//		String message = strBuilder.toString();
//	    return new ModelAndView("back-end/drinkOrderDetail/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
//	}


	
}
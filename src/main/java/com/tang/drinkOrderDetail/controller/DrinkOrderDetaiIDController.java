package com.tang.drinkOrderDetail.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Validated
@RequestMapping("/drinkOrderDetail")
public class DrinkOrderDetaiIDController {
	
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
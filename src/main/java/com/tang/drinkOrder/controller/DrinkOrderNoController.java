package com.tang.drinkOrder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;

@Controller
@Validated
@RequestMapping("/drinkOrder")
public class DrinkOrderNoController {

	@Autowired
	DrinkOrderService drinkOrderService;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailService;

	//從訂單紀錄來
	@PostMapping("getDrinkOrder")
	public String getOneDrinkOrder(
			@RequestParam("drinkOrderID") String drinkOrderID,
			@RequestParam("userID") String userID,
			@RequestParam("storeID") String storeID,
			@RequestParam("drinkOrderStartCreateTime") String drinkOrderStartCreateTime,
			@RequestParam("drinkOrderEndCreateTime") String drinkOrderEndCreateTime,
			@RequestParam("drinkOrderStatus") String drinkOrderStatus,			
			ModelMap model) {
		
		Map<String, String> map = new HashMap<>();
		map.put("drinkOrderID", drinkOrderID);
		map.put("userID", userID);
		map.put("storeID", storeID);
		map.put("drinkOrderStartCreateTime", drinkOrderStartCreateTime);
		map.put("drinkOrderEndCreateTime", drinkOrderEndCreateTime);
		map.put("drinkOrderStatus", drinkOrderStatus);
		
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAll(map);
		
		//沒查到,error準備
		if(drinkOrderList.isEmpty()) {	
			List<DrinkOrderVO> list = drinkOrderService.getAll();
			model.addAttribute("drinkOrderList", list);
			model.addAttribute("errorMessage", "查無此訂單");
		}
		
		//東西給前端
		model.addAttribute("drinkOrderList", drinkOrderList);
		return "back-end/drinkOrder/orderHistory";
	}
	
	
	//從訂單管理來
	@PostMapping("getUndoneDrinkOrder")
	public String getOneUndoneDrinkOrder(
			@RequestParam("drinkOrderID") String drinkOrderID,
			@RequestParam("userID") String userID,
			@RequestParam("storeID") String storeID,
			@RequestParam("drinkOrderStartCreateTime") String drinkOrderStartCreateTime,
			@RequestParam("drinkOrderEndCreateTime") String drinkOrderEndCreateTime,
			ModelMap model) {
		
		Map<String, String> map = new HashMap<>();
		map.put("drinkOrderID", drinkOrderID);
		map.put("userID", userID);
		map.put("storeID", storeID);
		map.put("drinkOrderStartCreateTime", drinkOrderStartCreateTime);
		map.put("drinkOrderEndCreateTime", drinkOrderEndCreateTime);
		map.put("drinkOrderStatus", "0");//查的東西皆為 未完成
		
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAll(map);
		
		//沒查到,error準備
		if(drinkOrderList == null) {	
			List<DrinkOrderVO> list = drinkOrderService.getAllUndone();
			model.addAttribute("drinkOrderList", list);
			model.addAttribute("errorMessage", "查無此訂單");
		}
		
		//東西給前端
		model.addAttribute("drinkOrderList", drinkOrderList);
		return "back-end/drinkOrder/orderManage";
	}
	
	

	
	
	
	//更改 訂單狀態 邏輯我要再想一下
	
	
	
	
//	//上面 Validated 的處理
//	@ExceptionHandler(value = { ConstraintViolationException.class })
//	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e,Model model) {
//		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//		StringBuilder strBuilder = new StringBuilder();
//		for(ConstraintViolation<?> violation : violations) {
//			strBuilder.append(violation.getMessage()+"<br>");
//		}
//		
//		List<DrinkOrderVO> list = drinkOrderService.getAll();
//		model.addAttribute("drinkOrderListData", list);
//		model.addAttribute("drinkOrderVO", new DrinkOrderVO());
//		List<DrinkOrderDetailVO> list2 = drinkOrderDetailService.getAll();
//		model.addAttribute("drinkOrderDetailListData", list2);
//		String message = strBuilder.toString();
//		return new ModelAndView("back-end/drinkOrder/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
//	}
}
//.
//.					   _ooOoo
//.					  o8888888o
//.					  88" . "88 
//.					  (| -_- |)
//.					  O\  =  /O
//.					___/`---'\____
//.				 .'  \\|     |//  `.
//.			    /  \\|||  :  |||//  \
//.			   /  _||||| -:- |||||_  \
//.			   |   | \\\  -  /// |   |
//.			   | \_|  ''\---/''  |   |
//.			   \  .-\__       __/-.  /
//.			 ___`. .'  /--.--\ `. . __
//.		  ."" '<  `.___\_<|>_/__.'  >'"".
//.      | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//.      \  \ `-.   \_ __\ /__ _/   .-` /  /
//. ======`-.____`-.___\_____/___.-`____.-'======
//.                    `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//.               佛祖保佑       永無BUG
package com.tang.drinkOrder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.store.model.StoreService;
import com.ellie.store.model.StoreVO;
import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;

@Controller
@Validated
@RequestMapping("/drinkOrder")
public class DrinkOrderBackController {

	@Autowired
	DrinkOrderService drinkOrderService;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailService;

	@Autowired
	StoreService storeSvc;
	//===============總公司端==================
	@GetMapping("orderHistory")
	public String orderHistory(ModelMap model) {
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAll();
		model.addAttribute("drinkOrderList",drinkOrderList);
		
		List<StoreVO> storeList = storeSvc.getAll();
		model.addAttribute("storeList",storeList);
		return "back-end/drinkOrder/orderHistory";
	}

	@GetMapping("orderManage")
	public String orderManage(ModelMap model) {
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAllUndone();
		model.addAttribute("drinkOrderList",drinkOrderList);
		
		List<StoreVO> storeList = storeSvc.getAll();
		model.addAttribute("storeList",storeList);
		return "back-end/drinkOrder/orderManage";
	}

	
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
		System.out.println(drinkOrderStatus);
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
	public String getUndoneDrinkOrder(
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
	
	
	@PostMapping("successDrinkOrder")
	public String successDrinkOrder(@RequestParam("drinkOrderID") String drinkOrderID, ModelMap model) {
		DrinkOrderVO drinkOrder = drinkOrderService.getOneDrinkOrder(Integer.valueOf(drinkOrderID));
		drinkOrder.setDrinkOrderStatus(Byte.valueOf("1"));
		drinkOrderService.updateDrinkOrder(drinkOrder);
		return "redirect:/drinkOrder/userDrinkOrder";
	}
	
	@PostMapping("sussesPaidDrinkOrder")
	public String sussesPaidDrinkOrder(@RequestParam("drinkOrderID") String drinkOrderID, ModelMap model) {
		DrinkOrderVO drinkOrder = drinkOrderService.getOneDrinkOrder(Integer.valueOf(drinkOrderID));
		drinkOrder.setDrinkOrderPayStatus(Byte.valueOf("1"));
		drinkOrderService.updateDrinkOrder(drinkOrder);
		return "redirect:/drinkOrder/userDrinkOrder";
	}
	
	//===============店家端==================
	@GetMapping("storeOrderHistory")
	public String storeOrderHistory(ModelMap model,HttpSession session) {
		StoreVO store = (StoreVO)session.getAttribute("Store");
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAllByStoreID(store.getStoreID());
		model.addAttribute("drinkOrderList",drinkOrderList);
		return "back-end/drinkOrder/storeOrderHistory";
	}
	
	@GetMapping("storeOrderManage")
	public String storeOrderManage(ModelMap model,HttpSession session) {
		StoreVO store = (StoreVO)session.getAttribute("Store");
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAllUndoneByStoreID(store.getStoreID());
		model.addAttribute("drinkOrderList",drinkOrderList);
		return "back-end/drinkOrder/storeOrderManage";
	}
	
	//從訂單紀錄來
	@PostMapping("getStoreDrinkOrder")
	public String getStoreDrinkOrder(
			@RequestParam("drinkOrderID") String drinkOrderID,
			@RequestParam("userID") String userID,
			@RequestParam("drinkOrderStartCreateTime") String drinkOrderStartCreateTime,
			@RequestParam("drinkOrderEndCreateTime") String drinkOrderEndCreateTime,
			@RequestParam("drinkOrderStatus") String drinkOrderStatus,			
			ModelMap model,
			HttpSession session) {
		
		StoreVO store = (StoreVO)session.getAttribute("store");
		
		Map<String, String> map = new HashMap<>();
		map.put("drinkOrderID", drinkOrderID);
		map.put("userID", userID);
		map.put("storeID", store.getStoreID().toString());
		map.put("drinkOrderStartCreateTime", drinkOrderStartCreateTime);
		map.put("drinkOrderEndCreateTime", drinkOrderEndCreateTime);
		map.put("drinkOrderStatus", drinkOrderStatus);
		
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAll(map);
		
		//沒查到,error準備
		if(drinkOrderList.isEmpty()) {	
			List<DrinkOrderVO> list = drinkOrderService.getAllByStoreID(store.getStoreID());
			model.addAttribute("drinkOrderList", list);
			model.addAttribute("errorMessage", "查無此訂單");
		}
		
		//東西給前端
		model.addAttribute("drinkOrderList", drinkOrderList);
		return "back-end/drinkOrder/storeOrderHistory";
	}

	//從訂單管理來
	@PostMapping("getStoreUndoneDrinkOrder")
	public String getStoreUndoneDrinkOrder(
			@RequestParam("drinkOrderID") String drinkOrderID,
			@RequestParam("userID") String userID,
			@RequestParam("drinkOrderStartCreateTime") String drinkOrderStartCreateTime,
			@RequestParam("drinkOrderEndCreateTime") String drinkOrderEndCreateTime,
			ModelMap model,
			HttpSession session) {
		
		StoreVO store = (StoreVO) session.getAttribute("store");
		
		Map<String, String> map = new HashMap<>();
		map.put("drinkOrderID", drinkOrderID);
		map.put("userID", userID);
		map.put("storeID", store.getStoreID().toString());
		map.put("drinkOrderStartCreateTime", drinkOrderStartCreateTime);
		map.put("drinkOrderEndCreateTime", drinkOrderEndCreateTime);
		map.put("drinkOrderStatus", "0");//查的東西皆為 未完成
		
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAll(map);
		
		//沒查到,error準備
		if(drinkOrderList == null) {	
			List<DrinkOrderVO> list = drinkOrderService.getAllUndoneByStoreID(store.getStoreID());
			model.addAttribute("drinkOrderList", list);
			model.addAttribute("errorMessage", "查無此訂單");
		}
		
		//東西給前端
		model.addAttribute("drinkOrderList", drinkOrderList);
		return "back-end/drinkOrder/storeOrderManage";
	}
	
	//*******************************
	//更改 訂單狀態 邏輯我要再想一下
	//*******************************
	
	
	
	
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
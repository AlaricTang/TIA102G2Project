package com.xyuan.study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;

public class Study01 {

	@Autowired
	DrinkOrderService drinkOrderService;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailService;
	
	//給後台其他頁面切到 訂單記錄的btn（無參數）,預設顯示list all
	@GetMapping("orderHistory")	
	public String orderHistory(ModelMap model) {
		List<DrinkOrderVO> list = drinkOrderService.getAll();
		model.addAttribute("drinkOrderList",list);
		return "back-end/drinkOrder/orderHistory";
	}
	
	//給後台其他頁面切到 訂單管理
	@GetMapping("orderManage")
	public String orderManage(ModelMap model) {
		List<DrinkOrderVO> list = drinkOrderService.getAllUndone();
		model.addAttribute("drinkOrderList",list);
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
			ModelMap model){
		
		//
		Map<String, String> map = new HashMap<>();
		map.put("drinkOrderID", drinkOrderID);
		map.put("userID", userID);
		map.put("storeID", storeID);
		map.put("drinkOrderStartCreateTime", drinkOrderStartCreateTime);
		map.put("drinkOrderEndCreateTime", drinkOrderEndCreateTime);
		map.put("drinkOrderStatus", drinkOrderStatus);
		
		//map是條件，然後把搜尋結果用list裝起來
		List<DrinkOrderVO> drinkOrderList = drinkOrderService.getAll(map);
		
		if(drinkOrderList.isEmpty()) {					//不是複合查詢的getAll
			List<DrinkOrderVO> list = drinkOrderService.getAll();
			model.addAttribute("drinkOrderList",list);
			model.addAttribute("errorMessage", "查無此訂單");
		}else {
			model.addAttribute("drinkOrderList",drinkOrderList);			
		}
		
		return "back-end/drinkOrder/orderHistory";
	}
	
	
	
//	map.put("drinkOrderEndCreateTime", drinkOrderEndCreateTime);
//	map.put("drinkOrderStatus", "0");//查的東西皆為 未完成
							//預設顯示所有"未完成"的訂單所以直接寫死
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

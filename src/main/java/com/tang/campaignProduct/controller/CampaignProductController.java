package com.tang.campaignProduct.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ken.drink.model.DrinkService;
import com.ken.drink.model.DrinkVO;
import com.tang.campaignProduct.model.CampaignProductVO;
import com.xyuan.product.model.ProductService;

@Controller
@RequestMapping("/campaign")
public class CampaignProductController {
	
	@Autowired
	ProductService productSvc;
	
	@Autowired
	DrinkService drinkSvc;
	
	//跳到 選擇商品頁面
		//呈現 未選擇的drink
	@GetMapping("UnSelectedDrink")
	public String UnSelectedDrink(ModelMap model, HttpSession session) {
		List<DrinkVO> drinkList =  drinkSvc.getAll();
		@SuppressWarnings("unchecked")
		List<CampaignProductVO> campaignDrink =  (List<CampaignProductVO>) session.getAttribute("campaignDrink");
		
		List<DrinkVO> filteredDrinkList = drinkList.stream()
				.filter(drink -> !campaignDrink.stream()
						.map(CampaignProductVO::getDrinkID)
						.anyMatch(id -> id.equals(drink.getDrinkID())))
				.collect(Collectors.toList());
						
		model.addAttribute("filteredDrinkList",filteredDrinkList);
		
		return "back-end/campaign/selectDrink";
	}
	
	//進行選擇 回原頁面
	@GetMapping("selectDrink")
	public String selectDrink(@RequestParam("selectDrinks") List<DrinkVO> selectDrinks, 
			HttpSession session,
			HttpServletRequest request) {
		
		//要放到session的 "campaignDrink"
		List<CampaignProductVO> campaignDrink = new ArrayList<>();
		
		for(DrinkVO selectDrink: selectDrinks) {
			Integer drinkID = selectDrink.getDrinkID();// 取出每個 DrinkVO 的 drinkID
			
			CampaignProductVO campaignProductVO = new CampaignProductVO();// 創建一個新的 CampaignProductVO 物件，並設定其 drinkID 屬性
			campaignProductVO.setDrinkID(drinkID);
			
			campaignDrink.add(campaignProductVO);
		}
		
		//會直接覆蓋 "campaignDrink"
		session.setAttribute("campaignDrink", campaignDrink);
		
		String refererUrl = request.getHeader("Referer");
		return "redirect:" + refererUrl;
	}
	
	

}

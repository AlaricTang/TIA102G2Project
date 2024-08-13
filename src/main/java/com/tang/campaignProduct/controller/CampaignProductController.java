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
import org.springframework.web.bind.annotation.PostMapping;
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

	
	
	//=============== 跳到 選擇商品頁面 ===============
	@GetMapping("goSelectedDrink")
	public String goSelectedDrink(ModelMap model, HttpSession session) {
		List<DrinkVO> drinkList =  drinkSvc.getAll();
			model.addAttribute("filteredDrinkList",drinkList);

		return "back-end/campaign/selectDrink";
	}
	
	//=============== 選擇完 回新增頁面 ===============
	@PostMapping("selectDrink")
	public String selectDrink(@RequestParam("selectDrinks") List<String> selectDrinks, 
			HttpSession session,
			HttpServletRequest request) {
		
		//要放到session的 "campaignDrink"
		List<CampaignProductVO> campaignProductList = new ArrayList<>();
		
		for(String selectDrink: selectDrinks) {
			CampaignProductVO campaignProductVO = new CampaignProductVO();// 創建一個新的 CampaignProductVO 物件，並設定其 drinkID 屬性
			campaignProductVO.setDrinkID(Integer.valueOf(selectDrink));
			
			campaignProductList.add(campaignProductVO);
		}
		
		//會直接覆蓋 "campaignDrinkList"
		session.setAttribute("campaignProductList", campaignProductList);
		
		String refererUrl = request.getHeader("Referer");
		System.out.println(refererUrl);
		return "redirect:/campaign/addCampaignPage";
	}

	
	
	
	//==================== update ======================================
	
	
	
	//=============== 跳到 選擇更新商品頁面 ===============
	@GetMapping("goSelectUpdateDrink")
	public String goSelectUpdateDrink(ModelMap model,HttpSession session) {
		List<DrinkVO> drinkList =  drinkSvc.getAll();
		model.addAttribute("filteredDrinkList",drinkList);
		
		return "back-end/campaign/selectUpdateDrink";
	}
	
	//=============== 選擇完 回更新頁面 ===============
	@PostMapping("selectUpdateDrink")
	public String selectUpdateDrink(@RequestParam("selectDrinks") List<String> selectProducts, 
			HttpSession session,
			HttpServletRequest request) {
		
		//要放到session的 "campaignProduct"
		List<CampaignProductVO> campaignProductList = new ArrayList<>();
		
		for(String selectProduct: selectProducts) {
			CampaignProductVO campaignProductVO = new CampaignProductVO();// 創建一個新的 CampaignProductVO 物件，並設定其 drinkID 屬性
			campaignProductVO.setDrinkID(Integer.valueOf(selectProduct));
			
			campaignProductList.add(campaignProductVO);
		}
		
		//會直接覆蓋 "campaignDrinkList"
		session.setAttribute("campaignProductList", campaignProductList);
		//然後再回到原頁面
		String beUpdateCampaignID = (String) session.getAttribute("beUpdateCampaignID");
		session.removeAttribute("beUpdateCampaignID");
		
		return "forward:/campaign/updateCampaignPage?campaignID=" + beUpdateCampaignID;
	}
}



//if(session.getAttribute("campaignDrink") != null) {
//@SuppressWarnings("unchecked")
//List<CampaignProductVO> campaignDrink =  (List<CampaignProductVO>) session.getAttribute("campaignProductList");			
//
//List<DrinkVO> filteredDrinkList = drinkList.stream()
//		.filter(drink -> !campaignDrink.stream()
//				.map(CampaignProductVO::getDrinkID)
//				.anyMatch(id -> id.equals(drink.getDrinkID())))
//		.collect(Collectors.toList());
//
//model.addAttribute("filteredDrinkList",filteredDrinkList);
//}else {
//}
//呈現 未選擇的drink


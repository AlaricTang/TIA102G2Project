package com.tang.jibeiProduct.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ken.drink.model.DrinkService;
import com.tang.jibeiProduct.model.JibeiProductService;
import com.tang.jibeiProduct.model.JibeiProductVO;

@Controller
@RequestMapping("/jibeiProduct")
public class JibeiProductBackController {
	
	@Autowired
	DrinkService drinkSvc;
	
	@Autowired
	JibeiProductService jibeiProductSvc;
	
	@GetMapping("jibeiProductManage")
	public String jibeiProductManage(ModelMap model) {
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();
		List<JibeiProductVO> offList = jibeiProductSvc.getOffJibeiProduct();
		model.addAttribute("onList",onList);
		model.addAttribute("offList",offList);
		return "back-end/jibeiProduct/jibeiProductManage";
	}
	
	@GetMapping("addPage")
	public String addPage(ModelMap model) {
		JibeiProductVO jibeiProduct = new JibeiProductVO();
		model.addAttribute("jibeiProduct",jibeiProduct);
		return "back-end/jibeiProduct/addPage";
	}
	
	@PostMapping("add")
	public String add(@Valid JibeiProductVO jibeiProduct,BindingResult result, 
			ModelMap model, HttpSession session) {
		//補齊
		jibeiProduct.setJibeiProductStatus(Byte.valueOf("0"));
		jibeiProduct.setMemberID((Integer)session.getAttribute("member"));
		//新增
		jibeiProductSvc.addJibeiProduct(jibeiProduct);
		//新增後回列表
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();
		List<JibeiProductVO> offList = jibeiProductSvc.getOffJibeiProduct();
		model.addAttribute("onList",onList);
		model.addAttribute("offList",offList);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/jibeiProduct/jibeiProductManage";
	}
	
	
	
	@PostMapping("updatePage")
	public String updatePage(@RequestParam("jibeiProductID") String jibeiProductID, ModelMap model) {
		JibeiProductVO jibeiProduct = jibeiProductSvc.getOneJibeiProduct(Integer.valueOf(jibeiProductID));
		//給要更新得 寄杯商品
		model.addAttribute("jibeiProduct",jibeiProduct);
		//給這個 寄杯商品 綁得飲品ID 後續前端去取他的圖片
		model.addAttribute("jibeiDrinkID",jibeiProduct.getDrinkID()); //前端  <img th:src="@{/drink/DBGifReader} + '?drinkID=' + ${jibeiDrinkID}">
		return "back-end/jibeiProduct/updatePage" ;
	}
	
	@PostMapping("update")
	public String update(@Valid JibeiProductVO jibeiProduct,BindingResult result, 
			ModelMap model,HttpSession session) {
		//補齊
		jibeiProduct.setMemberID((Integer)session.getAttribute("member"));
		Timestamp updateTime = new Timestamp(new Date().getTime());
		jibeiProduct.setJibeiProductUpdateTime(updateTime);
		//更新
		jibeiProductSvc.updateJibeiProduct(jibeiProduct);
		//更新後回列表
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();
		List<JibeiProductVO> offList = jibeiProductSvc.getOffJibeiProduct();
		model.addAttribute("onList",onList);
		model.addAttribute("offList",offList);
		model.addAttribute("success", "- (更新成功)");
		return "back-end/jibeiProduct/jibeiProductManage";
	}
	
	
	
	@PostMapping("delete")
	public String delete(@RequestParam("jibeiProductID") String jibeiProductID, ModelMap model) {
		jibeiProductSvc.deleteJibeiProduct(Integer.valueOf(jibeiProductID));
		//刪除後回列表
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();
		List<JibeiProductVO> offList = jibeiProductSvc.getOffJibeiProduct();
		model.addAttribute("onList",onList);
		model.addAttribute("offList",offList);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/jibeiProduct/jibeiProductManage"; 
	}


	
	

}

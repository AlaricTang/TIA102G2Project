package com.tang.jibeiProduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tang.jibeiProduct.model.JibeiProductService;
import com.tang.jibeiProduct.model.JibeiProductVO;

@Controller
@RequestMapping("/jibeiProduct")
public class JibeiProductFrontController {
	
	@Autowired
	JibeiProductService jibeiProductSvc;
	
	//取得所有 上架中 寄杯商品 
	@GetMapping("jibeiProductList")
	public String jibeiProductList(ModelMap model) {
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();
		model.addAttribute("jibeiProductList",onList);
		return "back-end/product/listAllProduct";
	}
	
}

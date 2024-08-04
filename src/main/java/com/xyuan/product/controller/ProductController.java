package com.xyuan.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xyuan.product.model.ProductService;
import com.xyuan.product.model.ProductVO;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productSvc;
	
	
	//取得所有商品列表
	@GetMapping("listAllProduct")
	public String listAllProduct(Model model) {
		List<ProductVO> list = productSvc.getAll();
		model.addAttribute("productListAll", list);
		return "back-end/product/listAllProduct";
	}
	
	
	//取得一個種類的商品	
	@PostMapping("getOneType_For_Display")
	public String getOne_For_Display(
		//從HTTP請求中獲取名為"productTag"的參數值。這個參數將以"String"形式傳遞給方法
		@RequestParam("productTag") String str_productTag,
		ModelMap model){
		
						//先將String轉為byte(因為productTag的型別是byte)
		Byte productTag = Byte.valueOf(str_productTag);
											//getByTag方法(寫在service)來根據產品標籤獲取產品列表
		List<ProductVO> productTaglist = productSvc.getByTag(productTag);
	
		model.addAttribute("productTaglist",productTaglist);
		return "back-end/product/listAllProduct";
	}

		
	//取得一個商品（點擊進入詳細頁面） 	
	@PostMapping("pdDetail")
	public String pdDetail(
			@RequestParam("productID") String str_productID, 
			ModelMap model) {
		
		Integer productID = Integer.valueOf(str_productID);
		ProductVO singleProduct = productSvc.getOneProduct(productID);
		
		model.addAttribute("singleProduct", singleProduct);
		return "back-end/product/singleProduct";
		
	}
		
	
	//點icon加入購物車
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

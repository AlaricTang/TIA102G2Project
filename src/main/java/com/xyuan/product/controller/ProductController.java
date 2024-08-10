package com.xyuan.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.user.model.UserVO;
import com.redis.jibeiProductCart.JibeiProductCartService;
import com.redis.productCart.ProductCartService;
import com.tang.jibeiProduct.model.JibeiProductService;
import com.tang.jibeiProduct.model.JibeiProductVO;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;
import com.xyuan.product.model.ProductService;
import com.xyuan.product.model.ProductVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productSvc;
	
	@Autowired
	JibeiProductService jibeiProductSvc;
	
	@Autowired
	ProductCartService productCartSvc;
	
	@Autowired
	JibeiProductCartService jibeiProductCartSvc;
	
	//取得所有商品列表
	/////要加上條件篩選"上架中"的商品
	
	/////到時候前端還要抓寄杯商品 ${productListAll}、${jibeiProductList}
	@GetMapping("listAllProduct")
	public String listAllProduct(ModelMap model) {
		
		List<ProductVO> onNormalProductList = productSvc.getOnProduct();
		model.addAttribute("productList", onNormalProductList);
		
		List<JibeiProductVO> onList = jibeiProductSvc.getOnJibeiProduct();	
		model.addAttribute("jibeiProductList", onList);
		
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
		
	
	//加入購物車
	@PostMapping("productAddToCart")
	public String productAddToCart(
			@RequestParam("productId") String productID,
			@RequestParam("orderAmount") String orderAmount,
			HttpSession session) throws IOException{

		ProductOrderDetailVO productItem = new ProductOrderDetailVO();
		productItem.setProductID(Integer.valueOf(orderAmount));
		productItem.setProductOrderDetailAmount(Integer.valueOf(orderAmount));
		
		UserVO user = (UserVO)session.getAttribute("user");
		
		
		productCartSvc.addCartItem(user.getUserId().toString(), productItem);
		
		return "redirect: /product/pdDetail?productId="+productID;
	}


	//跳轉到購物車page
		// 要取得user的購物車列表 (商品 and 寄杯)
			// user << session	
			// List<ProductDetailVO>		pdList	<< productCartSvc.getProductCart(userID)
			// List<JibeiProductDetailVO> 	jpdList	<< jibeiProductCartSvc.getJibeiProductCart(userID)
		//model.addAttribute("pdList",pdList)
		//model.addAttribute("jpdList",jpdList)
	//return ""
	 
	@PostMapping("switchToCartPage")
	public String switchToCartPage (ModelMap model, HttpSession session) throws IOException{
		
		UserVO user = (UserVO)session.getAttribute("user");
		
		List<ProductOrderDetailVO> pdList = productCartSvc.getProductCart(user.getUserId());
		List<JibeiOrderDetailVO> jpdList = jibeiProductCartSvc.getJibeiProductCart(user.getUserId());
		
		model.addAttribute("pdList",pdList);
		model.addAttribute("jpdList",jpdList);
	
		return "back-end/product/checkCart";
	}
	
	
	
	
	
	
	
	
	
	
	
}

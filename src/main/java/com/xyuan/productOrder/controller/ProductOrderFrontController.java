package com.xyuan.productOrder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ellie.user.model.UserVO;
import com.redis.productCart.ProductCartService;
import com.xyuan.product.model.ProductService;
import com.xyuan.product.model.ProductVO;
import com.xyuan.productOrder.model.ProductOrderService;
import com.xyuan.productOrder.model.ProductOrderVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailService;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Controller
@RequestMapping("/productOrder")
public class ProductOrderFrontController {

	@Autowired
	ProductOrderService productOrderSvc;
	
	@Autowired
	ProductCartService productCartService; 
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductOrderDetailService productOrderDetailSvc;

	
/* ------------------------------------------------------------------------------ */	
	
	// 新增訂單
	
	@GetMapping("productOrderPage")
	public String productOrderPage(ModelMap model, HttpSession session) throws IOException {
		int totalPrice = 0;
		ProductOrderVO productOrderVO = new ProductOrderVO();
		List<ProductOrderDetailVO> productCartList = new ArrayList<>();
		
	//訂購人
		//session中獲取名為"user"的資料(從前端form表單上設定的名字)
		//.getAttribute拿到的是object透過(UserVO)轉換為UserVO類型
		UserVO user = (UserVO)session.getAttribute("user");
		Integer userID = user.getUserId();		//使用getUserId()
		productOrderVO.setUserID(userID);		//用setUserID()將userID的資料放進productOrderVO
		
	//收件者姓名
		String str_receiverName = productCartService.getProductOrder("str_receiverName");
		productOrderVO.setReceiverName(str_receiverName);
		
	//收件者電話
		String str_receiverPhone = productCartService.getProductOrder("str_receiverPhone");
		productOrderVO.setReceiverPhone(str_receiverPhone);
		
	//收件者信箱
		String str_receiverMail = productCartService.getProductOrder("str_receiverMail");
		productOrderVO.setReceiverMail(str_receiverMail);
		
		
	//付款方式byte
		String str_productOrderPayM = productCartService.getProductOrder("str_productOrderPayM");
		productOrderVO.setProductOrderPayM(Byte.valueOf(str_productOrderPayM));
		
	//備註
		String str_productOrderNote = productCartService.getProductOrder("str_productOrderNote");
		productOrderVO.setProductOrderNote(str_productOrderNote);
		
		
	//購物車列表 + 確認訂單金額價錢
		productCartList = productCartService.getProductCart(userID);
		//迴圈變數名稱為drinkCartItem，其型別為DrinkOrderDetailVO，遍歷drinkCartList列表
		for(ProductOrderDetailVO productCartItem : productCartList) {
			ProductVO product = productService.getOneProduct(productCartItem.getProductID());
		}
		productOrderVO.setProductOrderAmount(totalPrice);
		
		session.setAttribute("productOrderVO", productOrderVO);
		
		//給前端
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("receiverName", str_receiverName);
		model.addAttribute("receiverPhone", str_receiverPhone);
		model.addAttribute("receiverMail", str_receiverMail);
		model.addAttribute("productOrderPayM", str_productOrderPayM);
		model.addAttribute("productOrderNote", str_productOrderNote);
		model.addAttribute("str_productOrderAmount", totalPrice);
		
		model.addAttribute("productCartList", productCartList);
		return "back-end/productOrder/productOrderPage";	
		
	}
	//////////////////////還沒加入比對庫存
	
	//堂安說
	//	下單,跳轉到成功頁面
	//  這裡含 存訂單明細的動作
	@PostMapping("order")
	synchronized public String order(ModelMap model,HttpSession session, RedirectAttributes redirectAttributes)throws IOException{
		
		//判斷訂單正確與否 & 存訂單
		ProductOrderVO productOrderVO = (ProductOrderVO)session.getAttribute("productOrder");
		
		productOrderVO.setProductOrderStatus(Byte.valueOf("0")); //訂單狀態 預設 未完成
		productOrderVO.setProductOrderPayStatus(Byte.valueOf("0")); //付款狀態 預設 未付款
		ProductOrderVO saveProductOrder = productOrderSvc.addandGetProductOrder(productOrderVO);	//存訂單
		
		Integer userID = productOrderVO.getUserID();
		List<ProductOrderDetailVO> cartProducts = productCartService.getProductCart(userID);
		Integer productOrderID = saveProductOrder.getProductOrderID();
		
		for(ProductOrderDetailVO productDetails : cartProducts) {
			productDetails.setProductOrderID(productOrderID);
			productOrderDetailSvc.addProductOrderDetail(productDetails);
		}
		
		if(productOrderVO.getProductOrderPayM() == 1) {					//如果為線上付款 去綠界
		productOrderVO.setProductOrderPayStatus(Byte.valueOf("1"));		//執行完 狀態設為 已付款
		}
		
		productCartService.deleteProductDetail(userID);		//下訂完 刪購物車明細
		
		redirectAttributes.addAttribute("saveProductOrder", saveProductOrder);
			return "redirect:/productOrder/orderSuccess";
	}
	
		@GetMapping("orderSuccess")
		public String orderSuccess(@ModelAttribute ("saveProductOrder") ProductOrderVO saveProductOrder, ModelMap model) {
			model.addAttribute("saveProductOrder", saveProductOrder);
			return "back-end/productOrder/orderSuccess";
		}
		
		@GetMapping("orderFail")
		public String orderFail(ProductOrderVO saveProductOrder, ModelMap model) {
			return "back-end/productOrder/orderFail";
		}
		
		@GetMapping("userProductOrder")
		public String userProductOrder(ModelMap model, HttpSession session) {
			UserVO user = (UserVO) session.getAttribute("user");
			List<ProductOrderVO> userProductList = productOrderSvc.getAllUserProductOrder(user.getUserId());
			model.addAttribute("userProductList", userProductList);
			return "back-end/productOrder/userProductOrder";
		}
		
		
		
		
		
	//捨不得刪掉
	//取得所有訂單列表
//		@GetMapping("productOrderHistory")
//		public String getAllProductOrders(ModelMap model) {
//			List<ProductOrderVO> productOrders = productOrderSvc.getAll();
//			model.addAttribute("productOrders", productOrders);
//		        return "back-end/productOrder/productOrderHistory";
//		}
	

}

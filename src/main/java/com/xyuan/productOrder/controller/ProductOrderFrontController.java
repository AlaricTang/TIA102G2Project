package com.xyuan.productOrder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ellie.store.model.StoreService;
import com.ellie.store.model.StoreVO;
import com.ellie.user.model.UserVO;
import com.redis.jibeiProductCart.JibeiProductCartService;
import com.redis.productCart.ProductCartService;
import com.tang.jibeiProduct.model.JibeiProductService;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailRepository;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;
import com.xyuan.product.model.ProductService;
import com.xyuan.productOrder.model.ProductOrderService;
import com.xyuan.productOrder.model.ProductOrderVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailRepository;
import com.xyuan.productOrderDetail.model.ProductOrderDetailService;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

@Controller
@RequestMapping("/productOrder")
public class ProductOrderFrontController {
	
	@Autowired
	private ProductOrderDetailRepository productOrderDetailRepository ;

	@Autowired
	ProductOrderService productOrderSvc;
	
	@Autowired
	ProductCartService productCartService; 
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductOrderDetailService productOrderDetailSvc;
	
	@Autowired
	StoreService storeSvc;
	
	@Autowired
	JibeiProductCartService jibeiProductCartSvc;

	@Autowired
	JibeiProductService jibeiProductSvc;

	@Autowired
	JibeiOrderDetailRepository jibeiOrderDetailRepository;

	
/* ------------------------------------------------------------------------------ */	
	
	// 新增訂單
	
	@GetMapping("productOrderPage")
	public String productOrderPage(ModelMap model, HttpSession session) throws IOException {
//		int totalPrice = 0;
		
		
//		List<ProductOrderDetailVO> productCartList = new ArrayList<>();/
		List<StoreVO> storeList = storeSvc.getAll();
		
	//訂購人
		//session中獲取名為"user"的資料(從前端form表單上設定的名字)
		//.getAttribute拿到的是object透過(UserVO)轉換為UserVO類型
		UserVO user = (UserVO)session.getAttribute("user");
		Integer userID = user.getUserId();		//使用getUserId()
			
		
//	購物車列表 + 確認訂單金額價錢
//		productCartList = productCartService.getProductCart(userID);
//		//迴圈變數名稱為drinkCartItem，其型別為DrinkOrderDetailVO，遍歷drinkCartList列表
//		for(ProductOrderDetailVO productCartItem : productCartList) {
//			ProductVO product = productService.getOneProduct(productCartItem.getProductVO().getProductID()) ;
//			totalPrice += product.getProductPrice();
//		}
		
		
		//給前端
		model.addAttribute("userID", userID);
//		model.addAttribute("productTotalPrice", totalPrice);
		model.addAttribute("storeList", storeList);
		
//		model.addAttribute("productCartList", productCartList);
		return "back-end/productOrder/productOrderPage";	
		
	}
	//////////////////////還沒加入比對庫存
	
	@GetMapping("productOrderFail")
	public String productOrderFail(HttpSession session, ModelMap model) {
		String orderFailMessage = (String)session.getAttribute("orderFailMessage");
		model.addAttribute("orderFailMessage",orderFailMessage);
		session.removeAttribute("orderFailMessage");
		return "back-end/productOrder/productOrderFail";
	}
	
	//有FK的版本			if(cartProduct.getProductOrderDetailAmount() > cartProduct.getProductVO().getProductInv()) {

	//	下單,跳轉到成功頁面
	//  這裡含 存訂單明細的動作
	@PostMapping("order")
	synchronized public String order(@Valid ProductOrderVO productOrderVO, ModelMap model,HttpSession session, RedirectAttributes redirectAttributes)throws IOException{
		Integer userID = productOrderVO.getUserID();
		
		//判斷訂單正確與否 & 存訂單
//		ProductOrderVO productOrderVO = (ProductOrderVO)session.getAttribute("productOrder");
		
		//補齊 店家 付款狀態 訂單狀態 付款方式
		productOrderVO.setProductOrderStatus(Byte.valueOf("0")); //訂單狀態 預設 未完成
		productOrderVO.setProductOrderPayStatus(Byte.valueOf("0")); //付款狀態 預設 未付款
//		productOrderVO.setProductOrderPayM(Byte.valueOf("0")); //付款方式 先統一店內付款 //前端已寫
		

		//productCartService使用getProductCart()透過userID獲得user的購物車裡面的商品
		List<ProductOrderDetailVO> cartProducts = productCartService.getProductCart(userID);
		List<JibeiOrderDetailVO> cartJibeiProducts = jibeiProductCartSvc.getJibeiProductCart(userID);

		//一般商品 庫存判斷
		String orderFailMessage = null; //給前端的失敗資訊
		for(ProductOrderDetailVO cartProduct : cartProducts) {
			if(cartProduct.getProductOrderDetailAmount() > cartProduct.getProductVO().getProductInv()) {
				orderFailMessage += cartProduct.getProductVO().getProductName() + ":庫存不足, ";
			}
		}		
		if(orderFailMessage != null) {
			session.setAttribute("orderFailMessage", orderFailMessage);
			return "redirect: /productOrder/productOrderFail";			
		}
		
//		//付款方式
//		if(productOrderVO.getProductOrderPayM() == 1) {	
//			//如果為線上付款 去綠界
//			productOrderVO.setProductOrderPayStatus(Byte.valueOf("1"));		//執行完 狀態設為 已付款
//		}
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		productOrderVO.setProductOrderCreateTime(timestamp);
		
		ProductOrderVO saveProductOrder = productOrderSvc.addProductOrder(productOrderVO);	//存訂單
//		Set<ProductOrderDetailVO> details = new HashSet<>();
		for(ProductOrderDetailVO productDetails : cartProducts) {
//			details.add(productDetails);
			productDetails.setProductOrderVO(saveProductOrder);
			Integer nowProductPrice = (productService.getOneProduct(productDetails.getProductVO().getProductID())).getProductPrice();
			productDetails.setProductOrderDetailPrice(nowProductPrice*(productDetails.getProductOrderDetailAmount()));
			productOrderDetailRepository.save(productDetails);
		}
		for(JibeiOrderDetailVO jbpdDetails: cartJibeiProducts) {
			jbpdDetails.setProductOrderVO(saveProductOrder);
			Integer nowProductPrice = (jibeiProductSvc.getOneJibeiProduct(jbpdDetails.getJibeiProductVO().getJibeiProductID())).getJibeiProductPrice();
			jbpdDetails.setJibeiOrderDetailPrice(nowProductPrice);
			
			
			jibeiOrderDetailRepository.insertJibeiOrderDetail(
					saveProductOrder.getProductOrderID(),
					jbpdDetails.getJibeiProductVO().getJibeiProductID(),
					jbpdDetails.getJibeiOrderDetailAmount(),
					jbpdDetails.getJibeiOrderDetailPrice());
		}
		
//		productOrderVO.setProductOrderDetails(details);
//		ProductOrderVO saveProductOrder = productOrderSvc.addProductOrder(productOrderVO);
		
//		productCartService.deleteProductOrder(userID);	//下訂完 刪購物人資訊 hash
		productCartService.deleteProductCart(userID);		//下訂完 刪購物車明細 List 
		jibeiProductCartSvc.deleteJibeiProductCart(userID);			
		
		return "redirect:/productOrder/orderSuccess";
	}
	
		@GetMapping("orderSuccess")
		public String orderSuccess( ModelMap model) {
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
		
		
		//會員頁面新增listAllByUserID
		@GetMapping("userListAllPdOrder")
		public String userListAllPdOrder( @RequestParam("userID") String str_userID, 
				ModelMap model) {
			
			Integer userID = Integer.valueOf(str_userID);
			List<ProductOrderVO> userListAllProduct = productOrderSvc.getAllUserProductOrder(userID);
			
			model.addAttribute("userListAllProduct", userListAllProduct );
			return "back-end/productOrder/userListAllProduct";
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

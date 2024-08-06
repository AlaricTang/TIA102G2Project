package com.xyuan.productOrderDetail.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.user.model.UserVO;
import com.xyuan.productOrder.model.ProductOrderService;
import com.xyuan.productOrder.model.ProductOrderVO;
import com.xyuan.productOrderDetail.model.ProductOrderDetailService;
import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;

public class ProductOrderDetailController {

	@Autowired
	ProductOrderDetailService productOrderDetailSvc;
	
	@Autowired
	ProductOrderService productOrderSvc;

	
	// 會員查訂單明細
	@GetMapping("userProductOrderDetail")
	public String userProductOrderDetail(
	    @RequestParam("ProductOrderID") String ProductOrderID, ModelMap model, HttpSession session) {

	    List<ProductOrderDetailVO> productOrderDetail = productOrderDetailSvc.getByProductOrderID(Integer.valueOf(ProductOrderID));
	    model.addAttribute("productOrderDetail", productOrderDetail);

	    UserVO user = (UserVO)session.getAttribute("user");
	    List<ProductOrderVO> productOrderList = productOrderSvc.getAllUserProductOrder(user.getUserId());
	    model.addAttribute("productOrderList", productOrderList);

	    return "back-end/productOrder/userProductOrder";
	}

	// 存訂單的動作 跟 存存訂單明細同步 所以寫在ProductOrderController

	//後臺 訂單紀錄 查詢明細
	@GetMapping("orderHistory_ProductOrderDetail")
	public String orderHistory_ProductOrderDetail(
	    @RequestParam("productOrderID") String productOrderID, ModelMap model) {

	    List<ProductOrderDetailVO> productOrderDetail = productOrderDetailSvc.getByProductOrderID(Integer.valueOf(productOrderID));
	    model.addAttribute("productOrderDetail", productOrderDetail);

	    List<ProductOrderVO> productOrderList = productOrderSvc.getAll();
	    model.addAttribute("productOrderList", productOrderList);
	    return "back-end/productOrder/orderHistory";
	}

	//後臺 訂單管理 查詢明細
	@GetMapping("orderManage_ProductOrderDetail")
	public String orderManage_ProductOrderDetail(
	    @RequestParam("productOrderID") String productOrderID, ModelMap model) {

	    List<ProductOrderDetailVO> productOrderDetail = productOrderDetailSvc.getByProductOrderID(Integer.valueOf(productOrderID));
	    model.addAttribute("productOrderDetail", productOrderDetail);

	    List<ProductOrderVO> productOrderList = productOrderSvc.getAllUndone();
	    model.addAttribute("productOrderList", productOrderList);
	    return "back-end/productOrder/orderManage";
	}
	
}

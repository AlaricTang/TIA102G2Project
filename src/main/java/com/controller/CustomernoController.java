package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import com.entity.CustomerVO;
import com.service.CustomerService;

//後台
@Controller
@Validated
@RequestMapping("/customer")
public class CustomernoController {
	
	@Autowired
	CustomerService customerSvc;
	
	//暫時不用
	//@Autowired
	//ReplyService replySvc;

	// 顯示所有頁面
	@GetMapping("listAllCustomer") 
	public String viewAllCustomers(ModelMap model) {
	    
	    // 獲取所有客戶留言
	    List<CustomerVO> customers = customerSvc.getAll();
	    model.addAttribute("customers", customers);
	    
	    // 返回顯示所有客戶留言的頁面的視圖名稱
	    return "back-end/customer/listAllCustomer"; 
	}
	
	
}
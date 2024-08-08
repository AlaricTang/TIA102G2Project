package com.LI.customer.controller;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import com.LI.customer.model.CustomerVO;
import com.LI.reply.model.ReplyService;
import com.LI.reply.model.ReplyVO;
import com.LI.customer.model.CustomerService;

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
	@GetMapping("select_page") 
	public String viewAllCustomers(ModelMap model) {
	    
	    // 獲取所有客戶留言
	    List<CustomerVO> customers = customerSvc.getAll();
	    model.addAttribute("customers", customers);
	    
	    // 返回顯示所有客戶留言的頁面的視圖名稱
	    return "back-end/customer/select_page"; 
	}
	
	
}
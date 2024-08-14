package com.LI.customer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.LI.customer.model.CustomerVO;
import com.LI.reply.model.ReplyVO;
import com.LI.customer.model.CustomerService;
import com.LI.reply.model.ReplyService;


// 前台
@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerSvc;

	// 暫時用不到
	//@Autowired
	//ReplyService replySvc;

	@GetMapping("select_page") // 測試用，用於開啟select_page
	public String getSelectPage() {
		return "back-end/customer/select_page";
	}
//here
	// 用戶端新增客戶留言+紀錄在後台
	@PostMapping("insert")
	public String insert(@Valid CustomerVO customerVO, BindingResult result,  ModelMap model) throws IOException {
		
		// 錯誤驗證：判斷所有欄位都有輸入
		
		//去除BindingResult中upFiles欄位的FieldError紀錄
		result = removeFieldError(customerVO, result, "addCustomerMessage");

		if (result.hasErrors()) {  
			// customerName驗證
			FieldError customerNameError = result.getFieldError("customerName");
			if (customerNameError != null && (customerNameError.getDefaultMessage() == null || customerNameError.getDefaultMessage().trim().length() == 0)) { 
				model.addAttribute("errorMessage", "聯絡姓名: 請輸入您的姓名");
			} else if(!customerNameError.getDefaultMessage().trim().matches("^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$")) {
				model.addAttribute("errorMessage", "聯絡姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}
			
			// customerEmail驗證
			FieldError customerEmailError = result.getFieldError("customerEmail");
			if (customerEmailError != null && (customerEmailError.getDefaultMessage() == null || customerEmailError.getDefaultMessage().trim().length() == 0)) {
				model.addAttribute("errorMessage", "電子信箱: 請輸入您的電子信箱");
	        } else if(!customerEmailError.getDefaultMessage().trim().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")){
				model.addAttribute("errorMessage", "電子信箱: 信箱格式不正確，請檢查是否輸入錯誤");
	        }
			
			// customerPhone驗證
			FieldError customerPhoneError = result.getFieldError("customerPhone");
			if (customerPhoneError != null && (customerPhoneError.getDefaultMessage() == null || customerPhoneError.getDefaultMessage().trim().length() == 0)) {
				model.addAttribute("errorMessage", "行動電話: 請輸入您的行動電話");
	        } else if(!customerPhoneError.getDefaultMessage().trim().matches("[0-9]{4}[0-9]{3}[0-9]{3}")){
				model.addAttribute("errorMessage", "行動電話: 電話格式不正確，請檢查是否輸入錯誤");
	        }			
			
			// customerSubject驗證
			FieldError customerSubjectError = result.getFieldError("customerSubject");
			if (customerSubjectError != null && (customerSubjectError.getDefaultMessage() == null || customerSubjectError.getDefaultMessage().trim().length() == 0)) {
				model.addAttribute("errorMessage", "留言主旨: 主旨不得為空");
	        } else if(!customerSubjectError.getDefaultMessage().trim().matches("^{1,20}$")){
				model.addAttribute("errorMessage", "留言主旨: 請將主旨保持在20字以內，以便我們更高效地處理您的需求。");
	        }
			
			// customerMessage驗證
			FieldError customerMessageError = result.getFieldError("customerMessage");
			if (customerMessageError != null && (customerMessageError.getDefaultMessage() == null || customerMessageError.getDefaultMessage().trim().length() == 0)) {
				model.addAttribute("errorMessage", "留言內容: 內容不得為空");
	        } else if(!customerMessageError.getDefaultMessage().trim().matches("^{1,200}$")){
				model.addAttribute("errorMessage", "留言內容: 請將內容保持在200字以內，以便我們更高效地處理您的需求。");
	        }			
			
				return "back-end/customer/select_page";
		}

		// 新增留言資料
		customerSvc.addCustomer(customerVO);

		//成功送出留言後，顯示成功訊息
		model.addAttribute("success", "訊息已送出成功! \r\n\" "+"感謝您的提問，我們將於周一至周五，\r\n"
				+ "上班時間AM09:00~PM17:00，為您做回覆");
		
		// 新增成功後，重導至原本留言頁面(select_page)
		return "redirect:/customer/select_page"; 
	}
	//here
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	private BindingResult removeFieldError(@Valid CustomerVO customerVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(customerVO, "customerVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}


	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
	    //
	    List<CustomerVO> list = customerSvc.getAll();
		model.addAttribute("customerListData", list);     // for select_page.html 第97 109行用
		//model.addAttribute("replyVO", new ReplyVO());  // for select_page.html 第133行用
		//List<ReplyVO> list2 = replySvc.getAll();
		//model.addAttribute("replyListData",list2);    // for select_page.html 第135行用
		String message = strBuilder.toString();
	    return new ModelAndView("back-end/customer/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
	}


	
}
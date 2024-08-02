package com.ken.cupRecord.controller;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ken.cupRecord.model.CupRecordService;
import com.ken.cupRecord.model.CupRecordVO;

@Controller
@Validated
@RequestMapping("/cupRecord")
public class CupnoRecordController {
	
	@Autowired
	CupRecordService cupRecordSvc;
	
//	@Autowired
//	UserService userSvc;
//	
//	@Autowired
//	CupService cupSvc;
//	
//	@Autowired
//	DrinkOrderService drinkOrderSvc;
//	
//	@Autowired
//	StoreService storeSvc;
	
	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="租借紀錄編號: 請勿空白")
		@Digits(integer = 4, fraction = 0, message = "租借紀錄編號: 請填數字-請勿超過{integer}位數")
		@Min(value = 1, message = "租借紀錄編號: 不能小於{value}")
		@Max(value = 100, message = "租借紀錄編號: 不能超過{value}")
		@RequestParam("cupRecordID") String cupRecordID,
		ModelMap model) {
		
		/***************************2.開始查詢資料*********************************************/
//		EmpService empSvc = new EmpService();
		CupRecordVO cupRecordVO = cupRecordSvc.getOneCupRecord(Integer.valueOf(cupRecordID));
		
		List<CupRecordVO> list = cupRecordSvc.getAll();
		model.addAttribute("cupRecordListData", list); // 查詢紀錄編號
//		model.addAttribute("userVO", new UserVO());  // for select_page.html 第133行用
//		List<UserVO> list2 = userSvc.getAll();		 // 會員查詢
//    	model.addAttribute("usertListData",list2);    // for select_page.html 第135行用
//		model.addAttribute("storeVO", new StoreVO());  // for select_page.html 第133行用
//		List<StoreVO> list2 = storeSvc.getAll();	   // 店家查詢
//    	model.addAttribute("storeListData",list2);    // for select_page.html 第135行用
		
		if (cupRecordVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/cupRecord/select_page";
		}
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("cupRecordVO", cupRecordVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第156行 -->
	
		return "back-end/cupRecord/select_page"; // 查詢完成後轉交select_page.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}
}

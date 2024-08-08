package com.ken.cup.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ellie.user.model.UserService;
import com.ellie.user.model.UserVO;
import com.ken.cup.model.CupService;
import com.ken.cup.model.CupVO;

@Controller
@Validated
@RequestMapping("/cup")
public class CupnoController {

	@Autowired
	CupService cupSvc;

//	@Autowired
//	StoreService storeSvc;
//	
//	@Autowired
//	MemberService memberSvc;
//	
	@Autowired
	UserService userSvc;

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			@NotEmpty(message = "環保杯編號: 請勿空白")
			@Digits(integer = 4, fraction = 0, message = "環保杯編號: 請填數字-請勿超過{integer}位數") 
			@Min(value = 1, message = "環保杯編號: 不能小於{value}") 
			@Max(value = 999, message = "環保杯編號: 不能超過{value}") 
			@RequestParam("cupID") String cupID,
			ModelMap model) {
		/***************************
		 * 2.開始查詢資料
		 *********************************************/
//		EmpService empSvc = new EmpService();
		CupVO cupVO = cupSvc.getOneCup(Integer.valueOf(cupID));

		List<CupVO> list = cupSvc.getAll();
		model.addAttribute("cupListData", list);
//		model.addAttribute("storeVO", new StoreVO()); // 用來查詢店家 複合查詢
//		List<StoreVO> list2 = storeSvc.getAll();   // 下拉是選單 列出店家 但我應該會用名字模糊查詢
//		model.addAttribute("storeListData", list2); 

		if (cupVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/cup/select_page";
		}
		/***************************
		 * 3.查詢完成,準備轉交(Send the Success view)
		 *****************/
		model.addAttribute("cupVO", cupVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第156行 -->

//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
		return "back-end/cup/select_page"; // 查詢完成後轉交select_page.html由其第158行insert
											// listOneEmp.html內的th:fragment="listOneEmp-div
	}


	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		// ==== 以下第92~96行是當前面第77行返回
		// /src/main/resources/templates/back-end/emp/select_page.html用的 ====
//	    model.addAttribute("empVO", new EmpVO());
//    	EmpService empSvc = new EmpService();
		List<CupVO> list = cupSvc.getAll();
		model.addAttribute("cupListData", list); // for select_page.html 第97 109行用
//		model.addAttribute("storeVO", new StoreVO());  // for select_page.html 第133行用
//		List<StoreVO> list2 = storeSvc.getAll();	// 店家用的
//    	model.addAttribute("storeListData",list2);    // for select_page.html 第135行用
		String message = strBuilder.toString();
		return new ModelAndView("back-end/cup/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}
//	// =================== 方法 1 店家畫面按下"租借按鈕" =============================
//			@PostMapping("userRentCup")
//			public String userRentCup(
//					@NotEmpty(message = "環保杯: 請勿空白")
//					@Digits(integer = 4, fraction = 0, message = "環保杯編號: 請填數字-請勿超過{integer}位數")
//					@Min(value = 1, message = "環保杯編號: 不能小於{value}") 
//					@Max(value = 6, message = "環保杯編號: 不能超過{value}") 
//					@RequestParam("cupID") String cupID,
//					@NotEmpty(message = "會員杯: 請勿空白")
//					@Digits(integer = 4, fraction = 0, message = "會員編號: 請填數字-請勿超過{integer}位數")
//					@Min(value = 1, message = "會員編號: 不能小於{value}") 
//					@Max(value = 2, message = "會員編號: 不能超過{value}")
//					@RequestParam("userID") String userID,
//					Model model) {
//
//				// 查詢杯子是否存在
//				CupVO cupVO = cupSvc.getOneCup(Integer.valueOf(cupID));
//				
//				if (cupVO == null) {
//					model.addAttribute("errorMessage", "查無資料");
//					return "back-end/cup/userRentCup"; // 導向回原頁面
//				}
//				
//				// 查詢會員是否存在
//		        UserVO userVO = userSvc.getOneUser(Integer.valueOf(userID)); // 假設有一個getOneMember方法
//		        
//		        if (userVO == null) {
//		            model.addAttribute("errorMessage", "查無會員資料");
//		            return "back-end/cup/userRentCup"; // 導向回原頁面
//		        }
//
//				// 更新杯子資料
//				cupVO.setUserID(Integer.valueOf(userID));
//				cupVO.setCupStatus(1); // 假設1表示已租借
//				LocalDateTime now = LocalDateTime.now();
//				Date sqlDate = Date.valueOf(now.toLocalDate());
//				cupVO.setCupRentDate(sqlDate);
//				cupSvc.updateCup(cupVO);
//				model.addAttribute("successMessage", "租借成功");
//				return "redirect:/cup/listAllCup";
//			}
}

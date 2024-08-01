package com.ken.notify.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ken.notify.model.NotifyService;
import com.ken.notify.model.NotifyVO;

@Controller
@Validated
@RequestMapping("/notify")
public class NotifynoController {

	@Autowired
	NotifyService notifySvc;

//	@Autowired
//	UserService userSvc;
	
	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(@RequestParam("notifyID") String notifyID,
			ModelMap model) {
		/***************************2.開始查詢資料*********************************************/
//		EmpService empSvc = new EmpService();
		NotifyVO notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyID));
		
		List<NotifyVO> list = notifySvc.getALL();
		model.addAttribute("notifyListData", list);     // for select_page.html 第97 109行用
//		model.addAttribute("userVO", new UserVO());  // for select_page.html 第133行用
		
//		List<UserVO> list2 = userSvc.getAll();
//		model.addAttribute("deptListData",list2);    // for select_page.html 第135行用
		
		if (notifyVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/notify/select_page";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("notifyVO", notifyVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第156行 -->
		
//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
		return "back-end/notify/select_page"; // 查詢完成後轉交select_page.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}
	
	 @GetMapping("findByUserID")
	    public String findByUserID(@RequestParam("userID") String userID, ModelMap model) {
	        List<NotifyVO> list = notifySvc.findByUserIDOrderByNotifyTimeDesc(Integer.valueOf(userID));
	        model.addAttribute("notifyList", list);

	        if (list.isEmpty()) {
	            model.addAttribute("errorMessage", "查無資料");
	            return "back-end/notify/select_page";
	        }

	        return "back-end/notify/select_page";
	    }
	 
	 @ExceptionHandler(value = { ConstraintViolationException.class })
	    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
	        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	        StringBuilder strBuilder = new StringBuilder();
	        for (ConstraintViolation<?> violation : violations) {
	            strBuilder.append(violation.getMessage() + "<br>");
	        }
	        List<NotifyVO> list = notifySvc.getALL();
	        model.addAttribute("notifyListData", list); // for select_page.html 第97 109行用
	        String message = strBuilder.toString();
	        return new ModelAndView("back-end/notify/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	    }
}

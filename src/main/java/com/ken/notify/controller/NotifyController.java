package com.ken.notify.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ken.notify.model.NotifyService;
import com.ken.notify.model.NotifyVO;

@Controller
@RequestMapping("/notify")
public class NotifyController {

	@Autowired
	NotifyService notifySvc;

//	@Autowired
//	UserService userSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addNotify")
	public String addNotify(ModelMap model) {
		NotifyVO notifyVO = new NotifyVO();
		model.addAttribute("notifyVO", notifyVO);
		return "back-end/notify/addNotify";
	}
	

    @PostMapping("insert")
	public String insert(@Valid NotifyVO notifyVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 沒有圖片 跳過
		/*************************** 2.開始新增資料 *****************************************/
		notifySvc.addNotify(notifyVO); // 單一查詢
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<NotifyVO> list = notifySvc.getALL();
		model.addAttribute("NotifyListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/notify/listAllNotify"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}

    
    /*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
    public String getOne_For_Update(@RequestParam("notifyno") String notifyID, ModelMap model) {
    	/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
    	NotifyVO notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyID));
    	
    	/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("notifyVO", notifyVO);
		return "back-end/notify/update_notify_input"; // 查詢完成後轉交update_emp_input.html
    }
    
    @PostMapping("update")
	public String update(@Valid NotifyVO notifyVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(notifyVO, result);

		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		notifySvc.updateNotify(notifyVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyVO.getNotifyID()));
		model.addAttribute("notifyVO", notifyVO);
		return "back-end/notify/listOneNotify"; // 修改成功後轉交listOneEmp.html
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(NotifyVO notifyVO, BindingResult result) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(notifyVO, "notifyVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	
	
	
}

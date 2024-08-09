package com.ken.cupRecord.controller;

import java.util.List;
import java.util.Map;
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

import com.ellie.store.model.StoreService;
import com.ellie.user.model.UserService;
import com.ken.cup.model.CupService;
import com.ken.cupRecord.model.CupRecordService;
import com.ken.cupRecord.model.CupRecordVO;
import com.tang.drinkOrder.model.DrinkOrderService;

@Controller
@RequestMapping("/cupRecord")
public class CupRecordController {

	@Autowired
	CupRecordService cupRecordSvc;
	
	@Autowired
	UserService userSvc;
	
	@Autowired
	CupService cupSvc;
	
	@Autowired
	DrinkOrderService drinkOrderSvc;
	
	@Autowired
	StoreService storeSvc;
	
	
	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addCupRecord")
	public String addEmp(ModelMap model) {
		CupRecordVO cupRecordVO = new CupRecordVO();
		model.addAttribute("cupRecordVO", cupRecordVO);
		return "back-end/cupRecord/addCupRecord";
	}
	
	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid CupRecordVO cupRecordVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(cupRecordVO, result);
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		cupRecordSvc.addCupRecord(cupRecordVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<CupRecordVO> list = cupRecordSvc.getAll();
		model.addAttribute("cupRecordListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/cupRecord/listAllCupRecord"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}
	
	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("cupRecordID") String cupRecordID, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		CupRecordVO cupRecordVO = cupRecordSvc.getOneCupRecord(Integer.valueOf(cupRecordID));
		
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("cupRecordVO", cupRecordVO);
		return "back-end/cupRecord/update_cupRecord_input"; // 查詢完成後轉交update_emp_input.html
	}
	
	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid CupRecordVO cupRecordVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(cupRecordVO, result);
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		cupRecordSvc.updateCupRecord(cupRecordVO);
		
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		cupRecordVO = cupRecordSvc.getOneCupRecord(Integer.valueOf(cupRecordVO.getCupID()));
		model.addAttribute("cupRecordVO", cupRecordVO);
		return "back-end/cupRecordVO/listOneCupRecordVO"; // 修改成功後轉交listOneEmp.html
	}
	
	// Delet方法跳過 
	
	// Figma目前查詢方法以文字方式查詢 但我覺得可能會用到下拉選單 又因為店家跟部門類似
	// 所以用第一個方法 直接用List抓全部店家
	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
//	@ModelAttribute("storeListData")
//	protected List<StoreVO> referenceListData() {
//		// DeptService deptSvc = new DeptService();
//		List<StoreVO> list = storeSvc.getAll();
//		return list;
//	}
	
	// 去除BindingResult中某個欄位的FieldError紀錄
		public BindingResult removeFieldError(CupRecordVO cupRecordVO, BindingResult result) {
			List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
					.collect(Collectors.toList());
			result = new BeanPropertyBindingResult(cupRecordVO, "cupRecordVO");
			for (FieldError fieldError : errorsListToKeep) {
				result.addError(fieldError);
			}
			return result;
		}
		
		/*
		 * This method will be called on select_page.html form submission, handling POST request
		 */
		@PostMapping("listCupRecords_ByCompositeQuery")
		public String listAllCupRecord(HttpServletRequest req, Model model) {
			Map<String, String[]> map = req.getParameterMap();
			List<CupRecordVO> list = cupRecordSvc.getAll(map);
			model.addAttribute("cupRecordListData", list); // for listAllEmp.html 第85行用
			return "back-end/cupRecord/listAllCupRecord";
		}
	
}

package com.ken.cup.controller;

import java.sql.Date;
import java.time.LocalDateTime;
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

import com.ken.cup.model.CupService;
import com.ken.cup.model.CupVO;

@Controller
@RequestMapping("/cup")
public class CupController {

	@Autowired
	CupService cupSvc;
	
//	@Autowired
//	StoreService storeSvc;
//	
//	@Autowired
//	MemberService memberSvc;
//	
//	@Autowired
//	UserService userSvc;
	
	/*
	 * This method will serve as addDrink.html handler.
	 */
	@GetMapping("addCup")
	public String addCup(ModelMap model) {
		CupVO cupVO = new CupVO();
		model.addAttribute("cupVO", cupVO);
		return "back-end/cup/addCup";
	}
	
	/*
	 * This method will be called on addDrink.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid CupVO cupVO, BindingResult result,ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(cupVO, result);
		if (result.hasErrors()) {
			return "back-end/cup/addCup";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		cupSvc.addCup(cupVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<CupVO> list = cupSvc.getAll();
		model.addAttribute("cupListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/cup/listAllCup"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}
	
	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("cupID") String cupID, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		CupVO cupVO = cupSvc.getOneCup(Integer.valueOf(cupID));
		
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("cupVO", cupVO);
		return "back-end/cup/update_cup_input"; // 查詢完成後轉交update_emp_input.html
	}
	
	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid CupVO cupVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(cupVO, result);
		if (result.hasErrors()) {
			return "back-end/cup/update_cup_input";
		}
		// 跟notify一樣 因為沒有圖片 所以這邊對照0205範例 不對圖片部分進行除錯
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		cupSvc.updateCup(cupVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		cupVO = cupSvc.getOneCup(Integer.valueOf(cupVO.getCupID()));
		model.addAttribute("cupVO", cupVO);
		return "back-end/cup/listOneCup"; // 修改成功後轉交listOneEmp.html
	}
	
	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("cupID") String cupID, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		cupSvc.deleteCup(Integer.valueOf(cupID));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<CupVO> list = cupSvc.getAll();
		model.addAttribute("cupListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/cup/listAllCup"; // 刪除完成後轉交listAllEmp.html
	}
	
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
	
	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${depMapData}" />
	 */
//	@ModelAttribute("deptMapData")
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(CupVO cupVO, BindingResult result) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(cupVO, "cupVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	/*
	 * This method will be called on select_page.html form submission, handling POST request
	 */
	@PostMapping("listCups_ByCompositeQuery")
	public String listAllCup(HttpServletRequest req, Model model) {
		Map<String, String[]> map = req.getParameterMap();
		List<CupVO> list = cupSvc.getAll(map);
		model.addAttribute("cupListData", list); // for listAllEmp.html 第85行用
		return "back-end/cup/listAllCup";
	}
	
	
	// ===================   方法 1 店家畫面按下"租借按鈕"   =============================
	@PostMapping("userRentCup")
	public String userRentCup(@RequestParam("cupID") Integer cupID, @RequestParam("userID") Integer userID, Model model) {
	    // 查詢杯子是否存在
	    CupVO cupVO = cupSvc.getOneCup(cupID);
	    if (cupVO == null) {
	        model.addAttribute("errorMessage", "杯子編號不存在");
	        return "back-end/cup/update_cup_input"; // 導向回原頁面
	    }

	    // 更新杯子資料
	    cupVO.setUserID(userID);
	    cupVO.setCupStatus(1); // 假設1表示已租借
	    LocalDateTime now = LocalDateTime.now();
	    Date sqlDate = Date.valueOf(now.toLocalDate());
	    cupVO.setCupRentDate(sqlDate);
	    cupSvc.updateCup(cupVO);
	    model.addAttribute("successMessage", "租借成功");
	    return "redirect:/cup/listAllCup";
	}
	
	// ===================   方法 2 店家畫面按下"(使用者)歸還按鈕"   =============================
	
	@PostMapping("userReturnCup")
	public String userReturnCup(@RequestParam("cupID") Integer cupID, Model model) {
	    // 查詢杯子是否存在
	    CupVO cupVO = cupSvc.getOneCup(cupID);
	    if (cupVO == null) {
	        model.addAttribute("errorMessage", "杯子編號不存在");
	        return "back-end/cup/userReturnCup"; // 導向回原頁面
	    }

	    // 更新杯子資料
	    cupVO.setCupStatus(0); // 假設0表示已歸還
	    cupVO.setUserID(null); // 清空userID
	    cupVO.setStoreID(null); // 清空storeID（若需要設置為特定的ID，可以在此處進行設定）
	    cupSvc.updateCup(cupVO);

	    model.addAttribute("successMessage", "杯子已成功歸還");
	    return "redirect:/cup/listAllCup"; // 成功後導向頁面
	}
	
}
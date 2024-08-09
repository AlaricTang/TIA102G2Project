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

import com.ellie.member.model.MemberService;
import com.ellie.member.model.MemberVO;
import com.ellie.store.model.StoreService;
import com.ellie.store.model.StoreVO;
import com.ellie.user.model.UserService;
import com.ellie.user.model.UserVO;
import com.ken.cup.model.CupService;
import com.ken.cup.model.CupVO;
import com.ken.cupRecord.model.CupRecordService;
import com.ken.cupRecord.model.CupRecordVO;

@Controller
@RequestMapping("/cup")
public class CupController {

	@Autowired
	CupService cupSvc;
	
	@Autowired
	StoreService storeSvc;
//	
	@Autowired
	MemberService memberSvc;
//	
	@Autowired
	UserService userSvc;
	
	@Autowired
	CupRecordService cupRecordSvc;
	
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
//		result = removeFieldError(cupVO, result);
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
//		result = removeFieldError(cupVO, result, );
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
	public BindingResult removeFieldError(CupVO cupVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
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
	// =================== 方法 1 店家畫面按下"租借按鈕" =============================
	@PostMapping("userRentCup")
	public String userRentCup(
	        @Valid CupVO cupVO,
	        @RequestParam("cupID") String cupID,
	        @RequestParam("userID") String userID,
	        BindingResult result,
	        Model model) {

	    // 查詢會員是否存在
	    UserVO userVO = userSvc.getOneUser(Integer.valueOf(userID));
	    if (userVO == null) {
	        model.addAttribute("errorMessage2", "查無會員資料");
	    }

	    // 查詢杯子是否存在
	    CupVO existingCupVO = cupSvc.getOneCup(Integer.valueOf(cupID));
	    if (existingCupVO == null) {
	        model.addAttribute("errorMessage1", "查無環保杯資料");
	    }
	    
	    
	    // 如果當前杯子狀態為1(已被出租)
	    if (existingCupVO.getCupStatus() == 1) {
	    	model.addAttribute("errorMessage3", "該杯子已被出租");
	    }
	    
	    if(result.hasErrors() || model.containsAttribute("errorMessage1") || model.containsAttribute("errorMessage2") || model.containsAttribute("errorMessage3")) {
			return "/back-end/cup/userRentCup";
		}

	    // 更新杯子資料
	    existingCupVO.setUserID(Integer.valueOf(userID));
	    existingCupVO.setCupStatus(1); // 假設1表示已租借
	    LocalDateTime now = LocalDateTime.now();
	    Date sqlDate = Date.valueOf(now.toLocalDate());
	    existingCupVO.setCupRentDate(sqlDate);
	    
	    
		 // 新增 CupRecord 記錄
	    CupRecordVO cupRecordVO = new CupRecordVO();
	    cupRecordVO.setUserID(Integer.valueOf(userID));
	    cupRecordVO.setCupID(Integer.valueOf(cupID));
	    cupRecordVO.setStoreRentID(existingCupVO.getStoreID());  // 假設 storeID 已設置在 cupVO 中
	    cupRecordVO.setCupRecordRentDate(sqlDate);

	    cupRecordSvc.addCupRecord(cupRecordVO);

	    cupSvc.updateCup(existingCupVO);
	    model.addAttribute("successMessage", "租借成功");
	    return "redirect:/cup/listAllCup";
	}
	
	// ===================   方法 2 店家畫面按下"(使用者)歸還按鈕"   =============================
	
	@PostMapping("userReturnCup")
	public String userReturnCup(
			@Valid CupVO cupVO,
	        @RequestParam("cupID") String cupID,
	        @RequestParam("storeID") String storeID,
	        BindingResult result,
	        Model model) {

	    // 查詢杯子是否存在
	    CupVO existingCupVO = cupSvc.getOneCup(Integer.valueOf(cupID));
	    if (existingCupVO == null) {
	        model.addAttribute("errorMessage1", "查無環保杯資料");
	    }
	    
	    // 查詢店家是否存在
	 	// 這邊如果有連FK 然後如果登入可以正常使用 這樣可以直接拿session
	 	// 但人生沒有如果
	 	StoreVO storeVO = storeSvc.getOneStore(Integer.valueOf(storeID));
	 	if (storeVO == null) {
	 		model.addAttribute("errorMessage2", "查無店家資料");
	 	}
	 	
	 	// 如果當前杯子狀態為0(尚未出租)
	    if (existingCupVO.getCupStatus() == 0) {
	    	model.addAttribute("errorMessage3", "該杯子已經被歸還(尚未出租)");
	    }
	    
	    if(result.hasErrors() || model.containsAttribute("errorMessage1") || model.containsAttribute("errorMessage2") ||  model.containsAttribute("errorMessage3")) {
			return "/back-end/cup/userReturnCup";
		}
	    
	    // 更新杯子資料
	    existingCupVO.setStoreID(Integer.valueOf(storeID));
	    existingCupVO.setUserID(null);
	    existingCupVO.setCupStatus(0); // 0表示已歸還
	    LocalDateTime now = LocalDateTime.now();
	    Date sqlDate = Date.valueOf(now.toLocalDate());
	    existingCupVO.setCupRentDate(sqlDate);
	    cupSvc.updateCup(existingCupVO);
	    
	    
	    // 新增 CupRecord 記錄
	    CupRecordVO cupRecordVO = new CupRecordVO();
	    cupRecordVO.setUserID(null); // 代表已經歸還，使用者為null
	    cupRecordVO.setCupID(Integer.valueOf(cupID)); // 杯子ID
	    cupRecordVO.setStoreReturnID(existingCupVO.getStoreID());  // 這個會吃到上面的setStoreID
	    cupRecordVO.setCupRecordReturnDate(sqlDate); // 歸還日期

	    cupRecordSvc.addCupRecord(cupRecordVO);
	    
	    model.addAttribute("successMessage", "歸還成功");
	    return "redirect:/cup/listAllCup";
	}
	
	// ===================   方法 3 店家畫面按下"報廢按鈕"   =============================
	@PostMapping("discardCup")
	public String discardCup(@Valid CupVO cupVO,@RequestParam("cupID") String cupID,
			@RequestParam("storeID") String storeID,
			BindingResult result,
			Model model) {
		
		// 查詢杯子是否存在
	    CupVO existingCupVO = cupSvc.getOneCup(Integer.valueOf(cupID));
	    if (existingCupVO == null) {
	        model.addAttribute("errorMessage1", "查無環保杯資料");
	    }
	    
	    // 如果當前杯子狀態為2(已經報廢)
	    if (existingCupVO.getCupStatus() == 2) {
	    	model.addAttribute("errorMessage3", "該杯子已經被報廢");
	    }
	    
	    
	    StoreVO storeVO = storeSvc.getOneStore(Integer.valueOf(storeID));
	    if (storeVO == null) {
	        model.addAttribute("errorMessage2", "查無店家資料");
	    }
	    
	    if(result.hasErrors() || model.containsAttribute("errorMessage1") || model.containsAttribute("errorMessage2") || model.containsAttribute("errorMessage3")) {
			return "/back-end/cup/discardCup";
		}
	    
	 // 更新杯子資料
	    existingCupVO.setStoreID(Integer.valueOf(storeID));
	    existingCupVO.setUserID(null);
	    existingCupVO.setCupStatus(2); // 2表示報廢
	    LocalDateTime now = LocalDateTime.now();
	    Date sqlDate = Date.valueOf(now.toLocalDate());
	    existingCupVO.setCupRentDate(sqlDate);

	    cupSvc.updateCup(existingCupVO);
	    model.addAttribute("successMessage", "報廢成功");
	    return "redirect:/cup/listAllCup";
	}
	
	// ===================   方法 4 計算還有多少杯子可以出租   =============================
	
	@PostMapping("countCups")
	public String countCups(@RequestParam("storeID") String storeID, Model model) {
	    Long cupCounts = cupSvc.countCupsByStoreAndStatus(Integer.valueOf(storeID));
	    model.addAttribute("cupCounts", cupCounts);
	    model.addAttribute("storeID", storeID); // 將storeID一併傳回給前端，以便顯示
	    return "/back-end/cup/select_page";
	}
	
	// ===================   方法 5 一次新增很多杯子   =============================
	
	// 開始新增
	@PostMapping("insertManyCup")
	public String insertManyCup(
			@RequestParam("storeID") String storeID, 
	        @RequestParam("memberID") String memberID, 
	        @RequestParam("number") Integer number, ModelMap model) {

	    // 假設所有杯子的 storeID 和 memberID 都相同

	    StoreVO storeVO = storeSvc.getOneStore(Integer.valueOf(storeID));
	    if (storeVO == null) {
	        model.addAttribute("errorMessage1", "查無門市資料");
	    }
	    
	    MemberVO memberVO = memberSvc.getOneMember(Integer.valueOf(memberID));
	    if (memberVO == null) {
	        model.addAttribute("errorMessage2", "查無員工資料");
	    }
	    
	    if (model.containsAttribute("errorMessage1") || model.containsAttribute("errorMessage2")) {
	        return "back-end/cup/addManyCupForm";
	    }

	    // 新增多個杯子資料
	    for (int i = 0; i < number ; i++) {
	    	CupVO cupVO = new CupVO();
	    	cupVO.setStoreID(Integer.valueOf(storeID));
	    	cupVO.setMemberID(Integer.valueOf(memberID));
	    	cupVO.setCupStatus(0);
	        cupSvc.addCup(cupVO);
	    }

	    model.addAttribute("successMessage", "成功新增 " + number + " 個杯子");
	    return "redirect:/cup/listAllCup";
	}

	
	// ===================   方法 6    =============================
}
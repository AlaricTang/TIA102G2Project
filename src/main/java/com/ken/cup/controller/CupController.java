package com.ken.cup.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
		
		// 取得當前更新前的資料
	    CupVO existingCupVO = cupSvc.getOneCup(cupVO.getCupID());
		 // 新增 CupRecord 記錄
	    CupRecordVO cupRecordVO = new CupRecordVO();
	    cupRecordVO.setUserID(cupVO.getUserID());
	    cupRecordVO.setCupID(cupVO.getCupID());
	    cupRecordVO.setStoreRentID(cupVO.getStoreID());
	    cupRecordVO.setCupRecordRentDate(existingCupVO.getCupRentDate()); // 記錄當時租借日期
	    
	    // 如果杯子已經歸還，則記錄歸還日期
	    if (cupVO.getCupStatus() == 0) {
	        LocalDateTime now = LocalDateTime.now();
	        Date sqlDate = Date.valueOf(now.toLocalDate());
	        cupRecordVO.setCupRecordReturnDate(sqlDate);
	        cupRecordVO.setStoreReturnID(cupVO.getStoreID());
	    }
	    
	    cupRecordSvc.addCupRecord(cupRecordVO);
	    
	 // 更新操作後，重新取得最新的 cupVO
	    cupVO = cupSvc.getOneCup(Integer.valueOf(cupVO.getCupID()));	
	    System.out.println(cupVO.getCupCreateDate());
	    
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
	    if (existingCupVO != null && existingCupVO.getCupStatus().equals(1)) {
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
	    

	    cupSvc.updateCup(existingCupVO);
	    model.addAttribute("successMessage", "租借成功");
	    
	    if(model.containsAttribute("successMessage")){
	    	// 新增 CupRecord 記錄
		    CupRecordVO cupRecordVO = new CupRecordVO();
		    cupRecordVO.setUserID(Integer.valueOf(userID));
		    cupRecordVO.setCupID(Integer.valueOf(cupID));
		    cupRecordVO.setStoreRentID(existingCupVO.getStoreID());  // 假設 storeID 已設置在 cupVO 中
		    cupRecordVO.setCupRecordRentDate(sqlDate);

		    cupRecordSvc.addCupRecord(cupRecordVO);
	    }
	    
	    
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
	    if (existingCupVO != null && existingCupVO.getCupStatus().equals(0)) {
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
	    
	    model.addAttribute("successMessage", "歸還成功");
	    
	    if(model.containsAttribute("successMessage")) {
	    	// 新增 CupRecord 記錄
		    CupRecordVO cupRecordVO = new CupRecordVO();
		    cupRecordVO.setUserID(null); // 代表已經歸還，使用者為null
		    cupRecordVO.setCupID(Integer.valueOf(cupID)); // 杯子ID
		    cupRecordVO.setStoreReturnID(existingCupVO.getStoreID());  // 這個會吃到上面的setStoreID
		    cupRecordVO.setCupRecordReturnDate(sqlDate); // 歸還日期
		    cupRecordSvc.addCupRecord(cupRecordVO);
	    }
	    
	    StoreVO store = storeSvc.getOneStore(Integer.valueOf(storeID));
	    store.setStoreCups(store.getStoreCups()+1);
	    storeSvc.updateStore(store); 
	    
	    return "redirect:/cup/listAllCup";
	}
	
	// 不太確定 報廢要不要設定紀錄需討論
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
	    if (existingCupVO != null && existingCupVO.getCupStatus().equals(2)) {
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
	@GetMapping("countCups")
	public String showCountCupsForm(Model model) {
	    model.addAttribute("cupCounts", 0L);
	    model.addAttribute("storeID", "");
	    return "backendHomepage"; 
	}
	
	
	@PostMapping("countCups")
	public String countCups(@RequestParam("storeID") String storeID, Model model) {
		
		// 檢查店家是否存在
	    StoreVO storeVO = storeSvc.getOneStore(Integer.valueOf(storeID));
	    if (storeVO == null) {
	        model.addAttribute("errorMessage", "店家不存在");
	        model.addAttribute("cupCounts", 0L); // 仍然顯示0作為杯子數量
	        model.addAttribute("storeID", storeID); // 將storeID一併傳回給前端，以便顯示
	        return "backendHomepage"; 
	    }
		
		
	    Long cupCounts = cupSvc.countCupsByStoreAndStatus(Integer.valueOf(storeID));
	    if (cupCounts == null) {
	        cupCounts = 0L;  // 設置默認值
	    }
	    
	    model.addAttribute("cupCounts", cupCounts);
	    model.addAttribute("storeID", storeID); // 將storeID一併傳回給前端，以便顯示
	    model.addAttribute("storeName", storeVO.getStoreName()); // 添加店家名稱
	    return "backendHomepage";
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
	    
	    
	    //更新店家杯數
	    StoreVO store = storeSvc.getOneStore(Integer.valueOf(storeID));
	    Integer storeCup = 0;
	    if(store.getStoreCups() == null) {
	    	storeCup = 0;
	    }else {
	    	storeCup = store.getStoreCups();
	    }
	    
	    store.setStoreCups(storeCup += number);
	    storeSvc.updateStore(store);
	    
	    model.addAttribute("successMessage", "成功新增 " + number + " 個杯子");
	    return "redirect:/cup/listAllCup";
	}

	
	// ===================   方法 6    =============================
	
	@GetMapping("userRentedCups")
	public String getUserRentedCups(Model model, HttpSession session) {
	    // 假設在模擬登入時將 userID 存入 session
		UserVO userVO = (UserVO) session.getAttribute("user");

//	    if (userID == null) {
//	        userID = 1; // 預設 userID 為 1 測試
//	    }
		if (userVO == null) {
	        System.out.println("User not found in session");
	        return "redirect:/index";
	    } else {
	        Integer userID = userVO.getUserId(); // 從 UserVO 中提取 userID
	        System.out.println("UserID found: " + userID);

	        List<CupVO> rentedCups = cupSvc.getRentedCupsByUser(userID);
	        model.addAttribute("rentedCups", rentedCups);
	        return "back-end/cup/userRentedCups";
	    }
	}
}
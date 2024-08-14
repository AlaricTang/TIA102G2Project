package com.ken.drink.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.ellie.user.model.UserVO;
import com.ken.drink.model.DrinkService;
import com.ken.drink.model.DrinkVO;
import com.redis.drinkCartTest.DrinkCartService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Controller
@RequestMapping("/drink")
public class DrinkController {
	
	@Autowired
	DrinkService drinkSvc;
	
	@Autowired
	DrinkCartService drinkCartSvc;
	
//	@Autowired
//	MemberService memberSvc;
	
	/*
	 * This method will serve as addDrink.html handler.
	 */
	@GetMapping("addDrink")
	public String addDrink(ModelMap model) {
		DrinkVO drinkVO = new DrinkVO();
		model.addAttribute("drinkVO", drinkVO);
		return "back-end/drink/addDrink";
	}
	

	@GetMapping("listAllDrink")
	public String listAllDrinks(ModelMap model) {
		List<DrinkVO> list = drinkSvc.getAll();
	    model.addAttribute("drinkListData", list);
	    return "back-end/drink/listAllDrink";
	}
	
	/*
	 * This method will be called on addDrink.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid DrinkVO drinkVO, BindingResult result, ModelMap model,
			@RequestParam("drinkPic") MultipartFile[] parts) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(drinkVO, result, "drinkPic");
		
		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "飲品圖片: 請上傳照片");
		}else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				drinkVO.setDrinkPic(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/drink/addDrink";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		drinkSvc.addDrink(drinkVO); // 單一查詢
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<DrinkVO> list = drinkSvc.getAll();
		model.addAttribute("drinkListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/drink/listAllDrink"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}
	
	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("drinkID") String drinkID, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		DrinkVO drinkVO = drinkSvc.getOneDrink(Integer.valueOf(drinkID));
		
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("drinkVO", drinkVO);
		return "back-end/drink/update_drink_input"; // 查詢完成後轉交update_emp_input.html
	}
	
	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update (@Valid DrinkVO drinkVO, BindingResult result, ModelMap model,
	@RequestParam("drinkPic") MultipartFile[] parts) throws IOException {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(drinkVO, result, "drinkPic");
		
		if (parts[0].isEmpty()) {
			// EmpService empSvc = new EmpService();
			byte[] upFiles = drinkSvc.getOneDrink(drinkVO.getDrinkID()).getDrinkPic();
			drinkVO.setDrinkPic(upFiles);
			System.out.println("1");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] upFiles = multipartFile.getBytes();
				drinkVO.setDrinkPic(upFiles);
			}
			System.out.println("2");
		}
		if (result.hasErrors()) {
			result.getFieldErrors().forEach(error -> {
		        System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
		    });
			return "back-end/drink/update_drink_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		drinkSvc.updateDrink(drinkVO);
		
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		drinkVO = drinkSvc.getOneDrink(Integer.valueOf(drinkVO.getDrinkID()));
		model.addAttribute("drinkVO", drinkVO);
		return "back-end/drink/listOneDrink"; // 修改成功後轉交listOneEmp.html
	}
	
	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("drinkID") String drinkID, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		drinkSvc.deleteDrink(Integer.valueOf(drinkID));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<DrinkVO> list = drinkSvc.getAll();
		model.addAttribute("drinkListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/drink/listAllDrink"; // 刪除完成後轉交listAllEmp.html
	}		
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(DrinkVO drinkVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(drinkVO, "drinkVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	/*
	 * This method will be called on select_page.html form submission, handling POST request
	 */
	@PostMapping("listDrinks_ByCompositeQuery")
	public String listAllDrink(HttpServletRequest req, Model model) {
		Map<String, String[]> map = req.getParameterMap();
		List<DrinkVO> list = drinkSvc.getAll(map);
		model.addAttribute("drinkListData", list); // for listAllEmp.html 第85行用
		return "back-end/drink/listAllDrink";
	}
	
	// =================== 方法 1 前台飲品菜單依照Tag分類 =============================
	
	@GetMapping("drinksByTag")
	public String getDrinksByTag(Model model) {
		
		Byte availableStatus = 1; // 假設 1 表示上架
		
	    List<DrinkVO> greenTeaDrinks = drinkSvc.getDrinksByTagAndStatus("綠茶", availableStatus);
	    List<DrinkVO> blackTeaDrinks = drinkSvc.getDrinksByTagAndStatus("紅茶", availableStatus);
	    List<DrinkVO> milkTeaDrinks = drinkSvc.getDrinksByTagAndStatus("奶茶", availableStatus);
	    List<DrinkVO> coffeeDrinks = drinkSvc.getDrinksByTagAndStatus("咖啡", availableStatus);
	    List<DrinkVO> machaDrinks = drinkSvc.getDrinksByTagAndStatus("抹茶", availableStatus);
	    List<DrinkVO> otherDrinks = drinkSvc.getDrinksByTagAndStatus("其他", availableStatus);
	    
	    model.addAttribute("greenTeaDrinks", greenTeaDrinks);
	    model.addAttribute("blackTeaDrinks", blackTeaDrinks);
	    model.addAttribute("milkTeaDrinks", milkTeaDrinks);
	    model.addAttribute("coffeeDrinks", coffeeDrinks);
	    model.addAttribute("machaDrinks", machaDrinks);
	    model.addAttribute("otherDrinks", otherDrinks);

	    return "back-end/drink/listAllDrinkFront";
	}
	
	// =================== 方法 2 購物車 =============================
	// 點擊進入詳細頁面
	@GetMapping("drinkDetail")
	public String drinkDetail(
			@RequestParam("drinkID") String str_drinkID,
			ModelMap model) {
		Integer drinkID = Integer.valueOf(str_drinkID);
		DrinkVO singleDrink = drinkSvc.getOneDrink(drinkID);
		
		model.addAttribute("singleDrink",singleDrink);
		return "back-end/drink/singleDrink";
		
	}
	
	// 加入購物車
	@PostMapping("drinkAddToCart")
	public String drinkAddToCart(
		@RequestParam("drinkID") String drinkID,
		@RequestParam("orderAmount") String orderAmount,
		HttpSession session ) throws IOException {
		
		DrinkOrderDetailVO drinkItem = new DrinkOrderDetailVO();
		drinkItem.setDrinkID(Integer.valueOf(drinkID));
		drinkItem.setDrinkOrderDetailAmount(Integer.valueOf(orderAmount));
		UserVO user = (UserVO)session.getAttribute("user");
		drinkCartSvc.addDrinkCartItem(user.getUserId(), drinkItem);
		return "redirect:/drink/drinkDetail?drinkID=" + drinkID;
	}
	
	// 加入購物車
	@GetMapping("drinkAddToCartOnListAlldrinkPage")
	public String drinkAddToCartOnListAlldrinkPage(
			@RequestParam("drinkID") String drinkID,
			@RequestParam("orderAmount") String orderAmount,
			HttpSession session ) throws IOException { 
		
		DrinkOrderDetailVO drinkItem = new DrinkOrderDetailVO();
		drinkItem.setDrinkID(Integer.valueOf(drinkID));
		drinkItem.setDrinkOrderDetailAmount(Integer.valueOf(orderAmount));
		UserVO user = (UserVO)session.getAttribute("user");
		drinkCartSvc.addDrinkCartItem(user.getUserId(), drinkItem);
		return "redirect:/drink/listAllDrinkFront";
	}
	
	// 切換
	@GetMapping("switchToDrinkCartPage")
	public String switchToDrinkCartPage(ModelMap model, HttpSession session) throws IOException{
		UserVO user = (UserVO)session.getAttribute("user");
		
		List<DrinkOrderDetailVO> ddList = drinkCartSvc.getDrinkCart(user.getUserId());
		
		model.addAttribute("ddList",ddList);
		
		return "back-end/drink/checkCart";
	}
}

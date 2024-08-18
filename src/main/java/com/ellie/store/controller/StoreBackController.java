package com.ellie.store.controller;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ellie.store.model.StoreVO;
import com.ellie.member.model.MemberVO;
import com.ellie.store.model.StoreService;

@Controller
@RequestMapping("/store")
public class StoreBackController {

    @Autowired
    StoreService storeService;

    // 後台登入頁面
    @GetMapping("/login")
    public String login() {
        return "back-end/store/login"; // 返回後台登入頁面
    }

    // 處理後台登入
    @PostMapping("/login")
    public String login(@RequestParam("storeAcc") String storeAcc, 
                        @RequestParam("storePwd") String storePwd,
                        @RequestParam("role") String role, // 店家端或員工端
                        HttpSession session, ModelMap model) {
        if (storeAcc != null && storePwd != null) {
            session.setAttribute("storeAcc", storeAcc);
            session.setAttribute("role", role); // 儲存角色
            return "redirect:/back-end/backendHomepage"; // 登入成功，重導至後台首頁
        } else {
            model.addAttribute("errorMessage", "帳號或密碼錯誤"); // 顯示錯誤訊息
            return "back-end/store/login"; // 登入失敗，返回登入頁面
        }
    }

    // 登出功能
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

        

    // 顯示店家列表
    @GetMapping("/listAllStores")
    public String listAllStores(ModelMap modelMap) {
        List<StoreVO> stores = storeService.getAll();
        modelMap.addAttribute("stores", stores);
        return "back-end/store/listAllStores"; // 返回顯示店家列表頁面
    }

    // 顯示新增店家頁面
    @GetMapping("/addStore")
    public String showAddStoreForm(ModelMap modelMap) {
        modelMap.addAttribute("storeVO", new StoreVO());
        return "back-end/store/addStore"; // 返回新增店家頁面
    }

    // 新增店家
    @PostMapping("/addStore")
    public String addStore(@ModelAttribute("storeVO") StoreVO storeVO, BindingResult result,
                           @RequestParam("storePic") MultipartFile[] parts, 
                           @RequestParam("storeOpenTime") String storeOpenTime, ModelMap model) throws IOException {
    	result = removeFieldError(storeVO, result, "storePic"); 
    	result = removeFieldError(storeVO, result, "storeOpenTime"); 
    	if (result.hasErrors()) {
            return "back-end/store/addStore"; // 如果有錯誤，返回新增店家頁面
        }

        // 檢查是否有圖片檔案
        if (parts != null && parts.length > 0 && !parts[0].isEmpty()) {
            storeVO.setStorePic(parts[0].getBytes());
        } else {
            storeVO.setStorePic(null); // 或者設置一個默認的圖片
        }
        // 因為SQL storeOpenTime not null傳入的值是字串所以要轉成sqlTime
        // sqlTime的轉法(string=>localtime=>sqltime=>塞進去storeOptime)
        if (storeOpenTime != null && storeOpenTime.length() > 0) {
        	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        	LocalTime localTime = LocalTime.parse(storeOpenTime,dateTimeFormatter);
        	Time sqlTime =Time.valueOf(localTime);
        	//強制八點關門
        	LocalTime localTime2 = LocalTime.parse("20:00",dateTimeFormatter);
        	Time sqlTime2 =Time.valueOf(localTime2);
        	storeVO.setStoreOpenTime(sqlTime);
        	storeVO.setStoreCloseTime(sqlTime2);
        }
        
        
        storeVO.setStoreMap("map");
		storeVO.setCreatedByMemberID(1);
        storeVO.setStoreCreateDate(new Timestamp(System.currentTimeMillis()));
        storeService.addStore(storeVO);

        List<StoreVO> list = storeService.getAll();
        model.addAttribute("stores", list);
        model.addAttribute("success", "- (新增成功)"); // 顯示新增成功訊息
        return "redirect:/store/listAllStores"; // 新增成功後重導至店家列表頁面
    }
    
 // 顯示單個店家詳細資訊
    @GetMapping("/listOneStore")
    public String listOneStore(@RequestParam("storeID") Integer storeID, ModelMap modelMap) {
        StoreVO storeVO = storeService.getOneStore(storeID);
        if (storeVO != null) {
            modelMap.addAttribute("storeVO", storeVO);
            return "back-end/store/listOneStore"; // 返回顯示單個店家詳細訊息的頁面
        } else {
            return "redirect:/store/listAllStores"; // 如果找不到該店家，回到店家列表頁面
        }
    }

    // 顯示修改店家頁面
    @GetMapping("/updateStore")
    public String showUpdateStoreForm(@RequestParam("storeID") Integer storeID, ModelMap modelMap) {
        StoreVO storeVO = storeService.getOneStore(storeID);
        if (storeVO != null) {
            modelMap.addAttribute("storeVO", storeVO);
            return "back-end/store/updateStore"; // 返回顯示店家修改頁面
        } else {
            return "redirect:/store/listAllStores"; // 如果找不到該店家，回到店家列表頁面
        }
    }

    // 修改店家資料
    @PostMapping("/updateStore")
    public String updateStore(@ModelAttribute("storeVO") StoreVO storeVO, BindingResult result,
                              @RequestParam("storePic") MultipartFile[] parts, 
                              @RequestParam("storeOpenTime") String storeOpenTime,ModelMap model) throws IOException {
    	result = removeFieldError(storeVO, result, "storePic"); 
    	result = removeFieldError(storeVO, result, "storeOpenTime"); 
    	if (result.hasErrors()) {
            return "back-end/store/updateStore"; // 如果有錯誤，返回修改店家頁面
        }

        // 檢查是否有圖片檔案
        if (parts != null && parts.length > 0 && !parts[0].isEmpty()) {
            storeVO.setStorePic(parts[0].getBytes());
        } else {
            // 如果沒有上傳新的圖片，保留原有圖片
            byte[] existingPic = storeService.getOneStore(storeVO.getStoreID()).getStorePic();
            storeVO.setStorePic(existingPic);
        }
        
     // 因為SQL storeOpenTime not null傳入的值是字串所以要轉成sqlTime
        // sqlTime的轉法(string=>localtime=>sqltime=>塞進去storeOptime)
        if (storeOpenTime != null && storeOpenTime.length() > 0) {
        	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        	LocalTime localTime = LocalTime.parse(storeOpenTime,dateTimeFormatter);
        	Time sqlTime =Time.valueOf(localTime);
        	storeVO.setStoreOpenTime(sqlTime);
        }

        storeService.updateStore(storeVO);
        model.addAttribute("success", "- (修改成功)"); // 顯示修改成功訊息

        // 更新後重新顯示修改頁面
        StoreVO updatedStore = storeService.getOneStore(storeVO.getStoreID());
        model.addAttribute("storeVO", updatedStore);
        return "back-end/store/updateStore"; // 返回顯示修改後的店家資料頁面
    }
    
    
    public BindingResult removeFieldError(StoreVO storeVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(storeVO, "storeVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}


//    // 根據複合查詢條件列出店家
//    @PostMapping("/listStores_ByCompositeQuery")
//    public String listStoresByCompositeQuery(HttpServletRequest req, ModelMap modelMap) {
//        Map<String, String[]> map = req.getParameterMap();
//        List<StoreVO> list = storeService.getAll(map);
//        modelMap.addAttribute("stores", list);
//        return "back-end/store/listAllStores"; // 返回顯示店家列表
//    }
}

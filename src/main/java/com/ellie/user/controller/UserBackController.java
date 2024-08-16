package com.ellie.user.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

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
import com.ellie.user.model.UserService;
import com.ellie.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserBackController {

    @Autowired
    UserService userService;

  
    
    // 顯示全部會員
    @GetMapping("listAllUsers")
    public String listAllUsers(ModelMap model) {
    	List<UserVO> list =userService.getAll();
        model.addAttribute("users", list); // 獲取所有會員
        for (UserVO vo : list) {
        	System.out.println(vo);
		}
        
        return "back-end/user/listAllUsers"; // 顯示全部會員的頁面
    }

    // 顯示單一會員
    @GetMapping("listOneUser")
    public String listOneUser(@RequestParam("userEmail") String userEmail, ModelMap model) {
        UserVO user = userService.findByEmail(userEmail); // 根據Email查詢單一會員
        if (user != null) {
            model.addAttribute("user", user);
            return "back-end/user/listOneUser"; // 顯示單一會員的頁面
        } else {
            model.addAttribute("errorMessage", "會員不存在");
            return "back-end/user/listAllUsers"; // 返回全部會員頁面
        }
    }

    // 更新會員資訊頁面
    @GetMapping("updateUser")
    public String updateUser(@RequestParam("userEmail") String userEmail, ModelMap model) {
        UserVO user = userService.findByEmail(userEmail); // 根據Email查詢單一會員
        if (user != null) {
            model.addAttribute("user", user);
            return "back-end/user/updateUser"; // 更新會員資訊頁面
        } else {
            model.addAttribute("errorMessage", "會員不存在");
            return "back-end/user/listAllUsers"; // 返回全部會員頁面
        }
    }

    // 處理更新會員資訊
    @PostMapping("updateUser")
    public String processUpdateUser(@ModelAttribute("user") UserVO user, BindingResult result,
    								@RequestParam("userEmail") String userEmail, 
    								@RequestParam("userBirth") String userBirth, ModelMap model) throws ParseException {
    	result = removeFieldError(user, result, "userBirth");
    	
    	if (result.hasErrors()) {
            return "back-end/user/listAllUsers"; // 如果有錯誤，返回頁面
        }
    	if (userBirth != null && userBirth.length() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = simpleDateFormat.parse(userBirth);
            user.setUserBirth(date);
            }
    	UserVO userVo = userService.findByEmail(userEmail);
    	user.setUserId(userVo.getUserId());
        userService.updateUser(user); // 更新會員資料
        model.addAttribute("successMessage", "會員資料更新成功");
        model.addAttribute("user", user);
        return "back-end/user/updateUser"; // 更新成功後留在原頁面
    }
    
    public BindingResult removeFieldError(UserVO userVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(userVO, "userVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }
}




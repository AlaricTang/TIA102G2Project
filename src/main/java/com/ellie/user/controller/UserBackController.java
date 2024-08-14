package com.ellie.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        model.addAttribute("users", userService.getAll()); // 獲取所有會員
        return "back-end/user/listAllUsers"; // 顯示全部會員的頁面
    }

    // 顯示單一會員
    @GetMapping("listOneUser")
    public String listOneUser(@RequestParam("userID") Integer userID, ModelMap model) {
        UserVO user = userService.findById(userID); // 根據ID查詢單一會員
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
    public String updateUser(@RequestParam("userID") Integer userID, ModelMap model) {
        UserVO user = userService.findById(userID); // 根據ID查詢單一會員
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
    public String processUpdateUser(@ModelAttribute("user") UserVO user, ModelMap model) {
        userService.updateUser(user); // 更新會員資料
        model.addAttribute("successMessage", "會員資料更新成功");
        return "redirect:/user/listAllUsers"; // 更新成功後重導回全部會員頁面
    }
}




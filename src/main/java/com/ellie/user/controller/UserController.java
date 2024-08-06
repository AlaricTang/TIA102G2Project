package com.ellie.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;


import com.ellie.user.model.UserVO;
import com.ellie.user.model.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("addUser")
    public String addUser(ModelMap model) {
        UserVO userVO = new UserVO();
        model.addAttribute("userVO", userVO);
        return "back-end/user/addUser";
    }

    @PostMapping("insert")
    public String insert(@Valid UserVO userVO, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "back-end/user/addUser";
        }
        userService.addUser(userVO);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/user/listAllUser";
    }

    @PostMapping("getOne_For_Update")
    public String getOneForUpdate(@RequestParam("userID") Integer userID, ModelMap model) {
        UserVO userVO = userService.getOneUser(userID);
        if (userVO == null) {
            model.addAttribute("errorMessage", "查無資料");
            return "back-end/user/select_page";
        }
        model.addAttribute("userVO", userVO);
        return "back-end/user/update_user_input";
    }

    @PostMapping("update")
    public String update(@Valid UserVO userVO, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "back-end/user/update_user_input";
        }
        userService.updateUser(userVO);
        model.addAttribute("success", "- (修改成功)");
        return "redirect:/user/listOneUser?userID=" + userVO.getUserId();
    }

    @PostMapping("delete")
    public String delete(@RequestParam("userID") Integer userID, ModelMap model) {
        userService.deleteUser(userID);
        model.addAttribute("success", "- (刪除成功)");
        return "redirect:/user/listAllUser";
    }

    @PostMapping("listUser_ByCompositeQuery")
    public String listUsersByCompositeQuery(HttpServletRequest req, Model model) {
        Map<String, String[]> map = req.getParameterMap();
        List<UserVO> list = userService.getAll(map);
        model.addAttribute("userListData", list);
        return "back-end/user/listAllUser";
    }
}

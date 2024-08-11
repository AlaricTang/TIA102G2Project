package com.ellie.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.ellie.user.model.UserVO;
import com.ellie.user.model.UserService;

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

	@GetMapping("select_page")
	public String selectPage() {
		return "back-end/user/select_page";
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
	public String listUsersByCompositeQuery(HttpServletRequest req, ModelMap model) {
		Map<String, String[]> map = req.getParameterMap();
		List<UserVO> list = userService.getAll(map);
		model.addAttribute("userListData", list);
		return "back-end/user/listAllUser";
	}

	// 會員基本資料
	@GetMapping("viewProfile")
	public String viewProfile(@RequestParam("userID") Integer userID, ModelMap model) {
		UserVO userVO = userService.getOneUser(userID);
		if (userVO == null) {
			model.addAttribute("errorMessage", "查無會員資料");
			return "back-end/user/select_page";
		}
		model.addAttribute("userVO", userVO);
		return "back-end/user/viewProfile"; // 導向會員資料頁面
	}

	// 註冊新用戶
	@GetMapping("register")
	public String register(ModelMap model) {
		UserVO userVO = new UserVO();
		model.addAttribute("userVO", userVO);
		return "back-end/user/register"; // 返回註冊頁面
	}

	@PostMapping("register")
	public String register(@Valid UserVO userVO, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "back-end/user/register"; // 如果驗證有錯誤，返回註冊頁面
		}
		userService.addUser(userVO); // 儲存會員資料到資料庫
		model.addAttribute("success", "註冊成功！");
		return "redirect:/user/login"; // 註冊成功後轉向登入頁面
	}

	// 登入
	@GetMapping("login")
	public String login() {
		return "back-end/user/login"; // 返回登入頁面
	}

	@PostMapping("login")
	public String login(@RequestParam("userEmail") String userEmail, @RequestParam("userPwd") String userPwd,
			HttpSession session, ModelMap model) {
		UserVO userVO = userService.findByEmail(userEmail);
		if (userVO == null || !userVO.getUserPwd().equals(userPwd)) {
			model.addAttribute("error", "電子郵件或密碼錯誤！");
			return "back-end/user/login"; // 登入失敗，返回登入頁面
		}
		session.setAttribute("user", userVO);
		return "redirect:/user/index"; // 登入成功後轉向用戶主頁
	}

	// 登出
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user/login"; // 登出後返回登入頁面
	}

	// 忘記密碼
	@GetMapping("forgotPassword")
	public String forgotPassword(ModelMap model) {
		return "back-end/user/forgotPassword"; // 返回忘記密碼頁面
	}

	@PostMapping("forgotPassword")
	public String forgotPassword(@RequestParam("userEmail") String userEmail, ModelMap model) {
		UserVO userVO = userService.findByEmail(userEmail);
		if (userVO == null) {
			model.addAttribute("error", "該電子郵件地址不存在！");
			return "back-end/user/forgotPassword"; // 如果Email不存在，返回忘記密碼頁面
		}
		// 模擬發送重設密碼的連結到用戶的Email
		model.addAttribute("success", "重設密碼的連結已發送到您的電子郵件地址。");
		return "back-end/user/forgotPassword"; // 提示用戶重設密碼連結已發送
	}

	// 重設密碼
	@GetMapping("resetPassword")
	public String resetPassword(@RequestParam("userEmail") String userEmail, ModelMap model) {
		UserVO userVO = userService.findByEmail(userEmail);
		if (userVO == null) {
			model.addAttribute("error", "無效的連結！");
			return "back-end/user/resetPassword"; // 如果Email無效，返回重設密碼頁面
		}
		model.addAttribute("userVO", userVO);
		return "back-end/user/resetPassword";
	}

	@PostMapping("resetPassword")
	public String resetPassword(@RequestParam("userEmail") String userEmail,
			@RequestParam("newPassword") String newPassword, ModelMap model) {
		UserVO userVO = userService.findByEmail(userEmail);
		if (userVO == null) {
			model.addAttribute("error", "無效的連結！");
			return "back-end/user/resetPassword"; // 如果Email無效，返回重設密碼頁面
		}
		userVO.setUserPwd(newPassword); // 更新會員的密碼
		userService.updateUser(userVO); // 儲存更新後的會員資料
		model.addAttribute("success", "密碼重設成功，請使用新密碼登入。");
		return "redirect:/user/login"; // 密碼重設成功後返回登入頁面
	}
}
